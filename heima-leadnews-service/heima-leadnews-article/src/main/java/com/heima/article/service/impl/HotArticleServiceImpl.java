package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.HotArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.vos.HotArticleVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.lbc.apis.wemedia.IWemediaClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.data.Json;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class HotArticleServiceImpl implements HotArticleService {
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private IWemediaClient wemediaClient;
    @Autowired
    private CacheService cacheService;
    @Override
    public void computeHotArticle() {
        //1、查询前五天的文章数据
        LocalDate dateParam = LocalDate.now().minusDays(5);
        List<ApArticle> apArticleList = apArticleMapper.findArticleByLast5days(dateParam);

        //2、计算文章的分值
        List<HotArticleVo> hotArticleVoList = computeScore(apArticleList);

        //3.为每个频道缓存30条分值较高的文章
        cacheTagToRedis(hotArticleVoList);

    }

    /**
     * 为每个频道缓存30条分值较高的文章
     * @param hotArticleVoList
     */
    private void cacheTagToRedis(List<HotArticleVo> hotArticleVoList) {
        ResponseResult responseResult = wemediaClient.getChannels();
        if (responseResult.getCode() == 200){
            String channelJson = JSON.toJSONString(responseResult.getData());
            List<WmChannel> wmChannels = JSON.parseArray(channelJson, WmChannel.class);
            //检索出每个频道的文章
            if (wmChannels != null && !wmChannels.isEmpty()){
                for (WmChannel wmChannel :wmChannels){
                    List<HotArticleVo> hotArticleVos = hotArticleVoList.stream().filter(x -> x.getChannelId().equals(wmChannel.getId())).collect(Collectors.toList());
                    //给文章进行排序，取三十条存入Redis key：频道id value：30条分值较高的文章
                    hotArticleVos = hotArticleVos.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
                    if (hotArticleVos.size() > 30){
                        hotArticleVos = hotArticleVos.subList(0,30);
                    }
                    cacheService.set(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + wmChannel.getId().toString(),JSON.toJSONString(hotArticleVos));
                }
            }
        }

        //设置推荐数据（不用根据频道）
        hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
        if (hotArticleVoList.size() > 30){
            hotArticleVoList = hotArticleVoList.subList(0,30);
        }
        cacheService.set(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG, JSON.toJSONString(hotArticleVoList));
    }

    /**
     * 计算文章分值
     * @param apArticleList
     * @return
     */
    private List<HotArticleVo> computeScore(List<ApArticle> apArticleList) {
        List<HotArticleVo> hotArticleVoList = new ArrayList<>();
        if (apArticleList != null && !apArticleList.isEmpty()){
            for (ApArticle apArticle : apArticleList){
                HotArticleVo hotArticleVo = new HotArticleVo();
                BeanUtils.copyProperties(apArticle,hotArticleVo);
                //计算文章的分值
                Integer score = 0;
                if (apArticle.getLikes() != null){
                    score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
                }
                if (apArticle.getViews() != null){
                    score += apArticle.getViews();
                }
                if (apArticle.getComment() != null){
                    score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
                }
                if (apArticle.getCollection() != null){
                    score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
                }
                hotArticleVo.setScore(score);
                hotArticleVoList.add(hotArticleVo);
            }
        }
        return hotArticleVoList;
    }
}
