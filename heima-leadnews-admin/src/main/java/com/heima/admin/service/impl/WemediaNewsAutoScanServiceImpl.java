package com.heima.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.heima.admin.feign.ArticleFeign;
import com.heima.admin.feign.WemediaFeign;
import com.heima.admin.mapper.AdChannelMapper;
import com.heima.admin.mapper.AdSensitiveMapper;
import com.heima.admin.service.WemediaNewsAutoScanService;
import com.heima.common.aliyun.GreeTextScan;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.fastdfs.FastDFSClient;
import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.model.wemedia.vo.WmNewsVo;
import com.heima.utils.common.SensitiveWordUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Log4j2
public class WemediaNewsAutoScanServiceImpl implements WemediaNewsAutoScanService {

    @Autowired
    private WemediaFeign wemediaFeign;

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @GlobalTransactional
    @Override
    public void autoScanByMediaNewsId(Integer id) {
        System.out.println("-------------------审核文章---------------");
        if (id == null) {
            log.error("当前的审核id空");
            return;
        }
        //1.根据id查询自媒体文章信息
        WmNews wmNews = wemediaFeign.findById(id);
        if (wmNews == null) {
            log.error("审核的自媒体文章不存在，自媒体的id:{}", id);
            System.out.println("审核的自媒体文章不存在");
            return;
        }

        //2.文章状态为4（人工审核通过）直接保存数据和创建索引
        if (wmNews.getStatus() == 4) {
            System.out.println("文章状态为4（人工审核通过）直接保存数据和创建索引");
            //保存数据
            saveAppArticle(wmNews);
            return;
        }

        //3.文章状态为8  发布时间小于等于当前时间 直接保存数据
        if (wmNews.getStatus() == 8 && wmNews.getPublishTime().getTime() <= System.currentTimeMillis()) {
            System.out.println("文章状态为8  发布时间小于等于当前时间 直接保存数据");
            //保存数据
            saveAppArticle(wmNews);
            return;
        }

        //4.文章状态为1，待审核
        if (wmNews.getStatus() == 1) {
            System.out.println("文章状态为1，待审核");
            updateWmNews(wmNews,(short) 3,"文章已提交，待人工审核");
            log.info("文章已提交，待人工审核");
        }
    }




    @Autowired
    private FastDFSClient fastDFSClient;

    @Value("${fdfs.url}")
    private String fileServerUrl;


    /**
     * 修改自媒体文章
     *
     * @param wmNews
     * @param status
     * @param msg
     */
    private void updateWmNews(WmNews wmNews, short status, String msg) {
        wmNews.setStatus(status);
        wmNews.setReason(msg);
        wemediaFeign.updateWmNews(wmNews);
    }

    @Autowired
    ArticleFeign articleFeign;

    /**
     * 保存app文章相关的数据
     *
     * @param wmNews
     */
    private void saveAppArticle(WmNews wmNews) {
        //保存app文章
        ApArticle apArticle = saveArticle(wmNews);
        //保存app文章配置
        saveArticleConfig(apArticle);
        //保存app文章内容
        saveArticleContent(apArticle,wmNews);

        //修改自媒体文章的状态为9
        wmNews.setArticleId(apArticle.getId());
        updateWmNews(wmNews,(short)9,"审核通过");

        //TODO es索引创建
        Map<String,Object> map = new HashMap();
        map.put("id",apArticle.getId().toString());
        map.put("publishTime",apArticle.getPublishTime());
        map.put("layout",apArticle.getLayout());
        map.put("images",apArticle.getImages());
        map.put("authorId",apArticle.getAuthorId());
        map.put("title",apArticle.getTitle());
        map.put("content",wmNews.getContent());
        //创建文档添加到索引库中
        IndexRequest indexRequest = new IndexRequest("app_info_article").id(apArticle.getId().toString()).source(map);
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("新增文章保存到WS中了");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建app端文章内容信息
     * @param apArticle
     * @param wmNews
     */
    private void saveArticleContent(ApArticle apArticle, WmNews wmNews) {
        ApArticleContent apArticleContent = new ApArticleContent();
        apArticleContent.setArticleId(apArticle.getId());
        apArticleContent.setContent(wmNews.getContent());
        articleFeign.saveArticleContent(apArticleContent);
    }

    /**
     * 创建app端文章配置信息
     * @param apArticle
     */
    private void saveArticleConfig(ApArticle apArticle) {
        ApArticleConfig apArticleConfig = new ApArticleConfig();
        apArticleConfig.setArticleId(apArticle.getId());
        apArticleConfig.setIsForward(true);
        apArticleConfig.setIsDelete(false);
        apArticleConfig.setIsDown(false);
        apArticleConfig.setIsComment(true);

        articleFeign.saveArticleConfig(apArticleConfig);
    }

    @Autowired
    AdChannelMapper adChannelMapper;

    /**
     * 保存文章
     * @param wmNews
     * @return
     */
    private ApArticle saveArticle(WmNews wmNews) {
        ApArticle apArticle = new ApArticle();
        apArticle.setTitle(wmNews.getTitle());
        apArticle.setLayout(wmNews.getType());
        apArticle.setImages(wmNews.getImages());
        apArticle.setCreatedTime(new Date());

        //获取作者相关信息
        Integer wmUserId = wmNews.getUserId();
        WmUser wmUser = wemediaFeign.findWmUserById(wmUserId);
        if(wmUser != null){
            String wmUserName = wmUser.getName();
            ApAuthor apAuthor = articleFeign.selectAuthorByName(wmUserName);
            if(apAuthor != null){
                apArticle.setAuthorId(apAuthor.getId().longValue());
                apArticle.setAuthorName(apAuthor.getName());
            }
        }

        //获取频道相关信息
        Integer channelId = wmNews.getChannelId();
        AdChannel channel = adChannelMapper.selectById(channelId);
        if(channel != null){
            apArticle.setChannelId(channel.getId());
            apArticle.setChannelName(channel.getName());
        }
        apArticle.setPublishTime(wmNews.getPublishTime());

        return articleFeign.saveArticle(apArticle);
    }

    @Override
    public PageResponseResult findNews(NewsAuthDto dto) {
        //分页查询
        PageResponseResult responseResult =  wemediaFeign.findList(dto);
        //有图片需要显示，需要fasfdfs服务器地址
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    @Override
    public ResponseResult findOne(Integer id) {
        //1参数检查
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2查询数据
        WmNewsVo wmNewsVo = wemediaFeign.findWmNewsVo(id);
        //结构封装
        ResponseResult responseResult = ResponseResult.okResult(wmNewsVo);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    @Override
    public ResponseResult updateStatus(Integer type, NewsAuthDto dto) {
        //1.参数检查
        if(dto == null || dto.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.查询文章
        WmNews wmNews = wemediaFeign.findById(dto.getId());
        if(wmNews == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //3.审核没有通过
        if(type.equals(0)){
            updateWmNews(wmNews,(short)2,dto.getMsg());
        }else if(type.equals(1)){
            //4.人工审核通过
            updateWmNews(wmNews,(short)9,"人工审核通过");
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}