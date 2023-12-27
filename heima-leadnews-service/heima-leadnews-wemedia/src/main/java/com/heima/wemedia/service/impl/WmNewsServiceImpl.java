package com.heima.wemedia.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.WemediaConstants;
import com.heima.common.exception.CustomException;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.Jar;
import org.json.JSONException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsServiceImpl  extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Override
    public ResponseResult findList(WmNewsPageReqDto dto) {
        dto.checkParam();

        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //状态查询
        if(dto.getStatus() != null) {
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }
        if(dto.getChannelId() != null){
            lambdaQueryWrapper.eq(WmNews::getChannelId,dto.getChannelId());
        }
        if(dto.getBeginPubDate() != null && dto.getEndPubDate() != null){
            lambdaQueryWrapper.between(WmNews::getCreatedTime,dto.getBeginPubDate(),dto.getEndPubDate());
        }
        if(StringUtils.isNotBlank(dto.getKeyword())){
            lambdaQueryWrapper.like(WmNews::getTitle,dto.getKeyword());
        }
        lambdaQueryWrapper.eq(WmNews::getUserId,WmThreadLocalUtil.getUser().getId());

        lambdaQueryWrapper.orderByDesc(WmNews::getPublishTime);

        page = page(page,lambdaQueryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }
    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Override
    public ResponseResult submitNews(WmNewsDto dto) throws JSONException {
        if(dto == null || dto.getContent() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto,wmNews);
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            String imageStr = StringUtils.join(dto.getImages(), ',');
            wmNews.setImages(imageStr);
        }
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
            wmNews.setType(null);
        }
        saveOrUpdateWmNews(wmNews);

        //判断是否为草稿
        if (dto.getStatus().equals(WmNews.Status.NORMAL.getCode())){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        List<String> materials = ectractUrlInfo(dto.getContent());//提取图片信息
        saveRelativeInfoForContent(materials,wmNews.getId());

        //不是草稿,保存文章封面图片与素材的关系，如果布局为自动，则匹配当前图片
        saveRelativeInfoForCover(dto,wmNews,materials);

        //审核文章
        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    private void saveRelativeInfoForCover(WmNewsDto dto, WmNews wmNews, List<String> materials) {
        List<String> images = dto.getImages();
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){//是否为自动匹配
            if (materials.size() >= 3){//多图
                wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            } else if (!materials.isEmpty()){//单图
                wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            } else {//无图
                wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }
            //修改文章
            if (images != null && !images.isEmpty()){
                wmNews.setImages(StringUtils.join(images,','));
            }
            updateById(wmNews);
        }
        if (images != null && !images.isEmpty()){
            saveRelativeInfo(images,wmNews.getId(),WemediaConstants.WM_CONTENT_REFERENCE);
        }
    }

    /**
     *
     * 处理文章内容图片与素材的关系
     * @param materials
     * @param newsId
     */
    private void saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        saveRelativeInfo(materials,newsId,WemediaConstants.WM_CONTENT_REFERENCE);
    }

    /**
     *保存文章图片的关系到数据库中
     * @param materials
     * @param newsId
     * @param type
     */
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        if (materials != null && !materials.isEmpty()) {
            List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));
            if (dbMaterials == null || dbMaterials.isEmpty()) {
                throw new CustomException(AppHttpCodeEnum.MATERIAL_REFERENCE_FAIL);
            }
            if (materials.size() != dbMaterials.size()) {
                throw new CustomException(AppHttpCodeEnum.MATERIAL_REFERENCE_FAIL);
            }
            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());
            wmNewsMaterialMapper.saveRelations(idList, newsId, type);
        }
    }

    private List<String> ectractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();
        List<Map> maps = JSON.parseArray(content, Map.class);
        for (Map map : maps){
            if(map.get("type").equals("image")){
                String imgUrl = (String) map.get("value");
                materials.add(imgUrl);
            }
        }
        return materials;
    }

    private void saveOrUpdateWmNews(WmNews wmNews){
        wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short) 1);//默认上架
        if (wmNews.getId() == null){
            save(wmNews);
        } else{
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId,wmNews.getId()));
            updateById(wmNews);
        }
    }
}
