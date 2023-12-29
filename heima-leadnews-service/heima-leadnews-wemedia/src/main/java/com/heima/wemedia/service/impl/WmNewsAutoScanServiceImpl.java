package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.common.baiduyun.GreenImageScan;
import com.heima.common.baiduyun.GreenTextScan;
import com.heima.common.tess4j.Tess4jClient;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.SensitiveWordUtil;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import com.lbc.apis.article.IArticleClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {
    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Override
    @Async //标明要异步调用
    public void autoScanWmNews(Integer id) throws JSONException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WmNews wmNews = wmNewsMapper.selectById(id);
        if (wmNews == null){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章不存在");
        }
        //从内容中提取文本内容和图片
        Map<String,Object> textAndImages = handleTextAndImages(wmNews);

        //自管理的敏感词过滤
        boolean isSensitive = handleSensitiveScan((String) textAndImages.get("content"),wmNews);
        if(!isSensitive) return;

        //2、审核文本内容，百度云接口
        boolean textCheck = handleScanText(wmNews, textAndImages);
        if (!textCheck) return;

//        3、审核图片的内容，百度云接口
        boolean imgCheck = handleScanImg(textAndImages, wmNews);
        if (!imgCheck) return;

//        4、审核成功，保存app端的相关数据
        ResponseResult responseResult = saveAppArticle(wmNews);
        if(!responseResult.getCode().equals(200)){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
        }

        //回填article_id
        wmNews.setArticleId((Long) responseResult.getData());
        updateWmnews(wmNews,(short) 9,"审核成功");

    }
    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;
    private boolean handleSensitiveScan(String content, WmNews wmNews) {
        boolean flag = true;
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(Wrappers.<WmSensitive>lambdaQuery().select(WmSensitive::getSensitives));
        List<String> sensitiveList = wmSensitives.stream().map(WmSensitive::getSensitives).collect(Collectors.toList());
        SensitiveWordUtil.initMap(sensitiveList);
        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if (!map.isEmpty()){
            updateWmnews(wmNews,(short) 2,"当前文章中存在敏感词" + map);
            flag = false;
        }
        return flag;
    }

    @Autowired
    private GreenTextScan greenTextScan;
    @Autowired
    private GreenImageScan greenImageScan;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private WmChannelMapper wmChannelMapper;
    @Resource
    private IArticleClient articleClient;
    @Autowired
    private Tess4jClient tess4jClient;

    private Map<String, Object> handleTextAndImages(WmNews wmNews) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> images = new ArrayList<>();
        if (StringUtils.isNotBlank(wmNews.getContent())){
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if (map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }
                if(map.get("type").equals("image")){
                    images.add((String) map.get("value"));
                }
            }
        }
        if (StringUtils.isNotBlank(wmNews.getImages())){
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("content",stringBuilder.toString());
        resultMap.put("images",images);
        return resultMap;
    }
    private boolean handleScanImg(Map<String, Object> textAndImages, WmNews wmNews) throws JSONException {
        List<String> images = (List<String>) textAndImages.get("images");
        List<String> imgUrlList = images.stream().distinct().collect(Collectors.toList());
        boolean flag = true;
        List<byte[]> imgList = new ArrayList<>();
        try {
            for (String img : imgUrlList) {
                byte[] bytes = fileStorageService.downLoadFile(img);
                //图片识别(ORC)
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(in);
                String result = tess4jClient.doOCR(bufferedImage);
                boolean isSensitive = handleSensitiveScan(result, wmNews);
                if (!isSensitive){
                    return isSensitive;
                }
                imgList.add(bytes);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for (byte[] bytes : imgList) {
            Map<String, String> map = greenImageScan.greenImageScan(bytes);
            String conclusion = map.get("conclusion");
            log.info("类型为：" + conclusion);
            if (conclusion.equals("不合规")) {
                updateWmnews(wmNews, (short) 2, (String) map.get("msg"));
                flag = false;
                return flag;
            }
            if (conclusion.equals("疑似")) {
                updateWmnews(wmNews, (short) 3, (String) map.get("msg"));
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    private boolean handleScanText(WmNews wmNews, Map<String, Object> textAndImages) throws JSONException {
        String content = (String) textAndImages.get("content");
        boolean flag = true;
        if (content.isEmpty()) return flag;
        Map<String, String> map = greenTextScan.greenTextScan(content);
        String conclusion = map.get("conclusion");
        log.info("类型为：" + conclusion);
        if (conclusion.equals("不合规")) {
            updateWmnews(wmNews, (short) 2, (String) map.get("msg"));
            flag = false;
            return flag;
        }
        if (conclusion.equals("疑似")) {
            updateWmnews(wmNews, (short) 3, (String) map.get("msg"));
            flag = false;
            return flag;
        }
        return flag;
    }
    private ResponseResult saveAppArticle(WmNews wmNews) {
        ArticleDto dto = new ArticleDto();
        BeanUtils.copyProperties(wmNews,dto);
        dto.setLayout(wmNews.getType());
        WmChannel wmChannel = wmChannelMapper.selectById(dto.getChannelId());

//            频道
        if (wmChannel != null) {
            dto.setChannelName(wmChannel.getName());
        }

//            作者
        dto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if(wmUser != null){
            dto.setAuthorName(wmUser.getName());
        }

        //设置文章id
        if(wmNews.getArticleId() != null){
            dto.setId(wmNews.getArticleId());
        }
        dto.setCreatedTime(new Date());
        ResponseResult responseResult = articleClient.saveArticle(dto);
        return responseResult;
    }
    private void updateWmnews(WmNews wmNews, short status, String reason) {
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }
}
