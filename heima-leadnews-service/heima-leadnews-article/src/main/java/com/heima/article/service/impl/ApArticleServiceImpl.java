package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.article.service.ArticleFreemarkerService;
import com.heima.common.constants.ArticleConstants;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.constants.HotArticleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.vos.HotArticleVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mess.ArticleVisitStreamMess;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
//@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService{
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ArticleFreemarkerService articleFreemarkerService;

    private final static short MAX_PAGE_SIZE = 50;
    public ResponseResult load(ArticleHomeDto dto,Short type){
        Integer size = dto.getSize();
        if(size == null || size == 0){
            size = 10;
        }
        size = Math.min(size,MAX_PAGE_SIZE);
        dto.setSize(size);
        if(!type.equals(ArticleConstants.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            type = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        if (StringUtils.isBlank(dto.getTag())){
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }
        if (dto.getMaxBehotTime() == null){
            dto.setMaxBehotTime(new Date());
        }
        if (dto.getMinBehotTime() == null){
            dto.setMinBehotTime(new Date());
        }

        List<ApArticle> articleList = apArticleMapper.loadArticleList(dto, type);
        return ResponseResult.okResult(articleList);
    }
    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(dto,apArticle);
        if(dto.getId() == null){//不存在id则保存文章
            save(apArticle);
            ApArticleConfig apArticleConfig = new ApArticleConfig(apArticle.getId());
            apArticleConfigMapper.insert(apArticleConfig);

            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);
        } else{//存在则修改文章
            updateById(apArticle);
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, dto.getId()));
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }

        articleFreemarkerService.buildArticleToMinIO(apArticle,dto.getContent());
        return ResponseResult.okResult(apArticle.getId());
    }

    @Autowired
    private CacheService cacheService;

    /**
     *加载文章行为-数据回显
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {

        //0.检查参数
        if (dto == null || dto.getArticleId() == null || dto.getAuthorId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //{ "isfollow": true, "islike": true,"isunlike": false,"iscollection": true }
        boolean isfollow = false, islike = false, isunlike = false, iscollection = false;

        ApUser user = AppThreadLocalUtil.getUser();
        if(user != null){
            //喜欢行为
            String likeBehaviorJson = (String) cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            if(StringUtils.isNotBlank(likeBehaviorJson)){
                islike = true;
            }
            //不喜欢的行为
            String unLikeBehaviorJson = (String) cacheService.hGet(BehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            if(StringUtils.isNotBlank(unLikeBehaviorJson)){
                isunlike = true;
            }
            //是否收藏
            String collctionJson = (String) cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR+user.getId(),dto.getArticleId().toString());
            if(StringUtils.isNotBlank(collctionJson)){
                iscollection = true;
            }

            //是否关注
            Double score = cacheService.zScore(BehaviorConstants.APUSER_FOLLOW_RELATION + user.getId(), dto.getAuthorId().toString());
            System.out.println(score);
            if(score != null){
                isfollow = true;
            }

        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isfollow", isfollow);
        resultMap.put("islike", islike);
        resultMap.put("isunlike", isunlike);
        resultMap.put("iscollection", iscollection);

        return ResponseResult.okResult(resultMap);
    }

    @Override
    public ResponseResult load2(ArticleHomeDto dto, Short type, boolean firstPage) {
        if(firstPage){
            String jsonStr = cacheService.get(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + dto.getTag());
            if (jsonStr != null){
                List<HotArticleVo> hotArticleVoList = JSON.parseArray(jsonStr, HotArticleVo.class);
                return ResponseResult.okResult(hotArticleVoList);
            }
        }
        return load(dto,type);
    }

    @Override
    public void updateScore(ArticleVisitStreamMess mess) {
        //更新文章的点赞、阅读、收藏、评论的数量
        ApArticle apArticle = updateArticle(mess);

        //根据权重计算分值
        Integer score = computeScore(apArticle);
        score *= 3;

        //替换当前文章对应频道的热点数据
        replaceDateToRedis(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + apArticle.getChannelId(), apArticle, score);

        //替换推荐的热点数据
        replaceDateToRedis(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG, apArticle, score);

    }

    /**
     * 替换数据并存入Redis
     * @param key
     * @param apArticle
     * @param score
     */
    private void replaceDateToRedis(String key, ApArticle apArticle, Integer score) {
        String articleListStr = cacheService.get(key);
        if (StringUtils.isNotBlank(articleListStr)) {
            boolean flag = true;

            List<HotArticleVo> hotArticleVoList = JSON.parseArray(articleListStr, HotArticleVo.class);
            //如果Redis中存在该文章则更新分值
            for (HotArticleVo hotArticleVo : hotArticleVoList) {
                if (hotArticleVo.getId().equals(apArticle.getId())) {
                    hotArticleVo.setScore(score);//更新分值
                    flag = false;
                    break;
                }
            }
            //不存在则查找分值最小的数据，并比较判断是否替换
            if (flag) {
                if (hotArticleVoList.size() >= 30) {
                    hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
                    HotArticleVo lastHot = hotArticleVoList.get(hotArticleVoList.size() - 1);
                    if (lastHot.getScore() < score) {
                        hotArticleVoList.remove(lastHot);
                        HotArticleVo hotArticleVo = new HotArticleVo();
                        BeanUtils.copyProperties(apArticle, hotArticleVo);
                        hotArticleVo.setScore(score);
                        hotArticleVoList.add(hotArticleVo);
                    }
                } else {
                    HotArticleVo hotArticleVo = new HotArticleVo();
                    BeanUtils.copyProperties(apArticle, hotArticleVo);
                    hotArticleVo.setScore(score);
                    hotArticleVoList.add(hotArticleVo);
                }
            }
            //排序后缓存到Redis中
            hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
            cacheService.set(key, JSON.toJSONString(hotArticleVoList));
        }
    }

    /**
     * 更新文章行为数量
     * @param mess
     */
    private ApArticle updateArticle(ArticleVisitStreamMess mess) {
        ApArticle apArticle = getById(mess.getArticleId());
        apArticle.setLikes((apArticle.getLikes() == null ? 0 : apArticle.getLikes()) + mess.getLike());
        apArticle.setCollection((apArticle.getCollection() == null ? 0 : apArticle.getCollection()) + mess.getCollect());
        apArticle.setComment((apArticle.getComment() == null ? 0 : apArticle.getComment()) + mess.getComment());
        apArticle.setViews((apArticle.getViews() == null ? 0 : apArticle.getViews()) + mess.getView());
        updateById(apArticle);
        return apArticle;
    }

    /**
     * 计算分值
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        Integer score = 0;
        if(apArticle.getLikes() != null){
            score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if(apArticle.getViews() != null){
            score += apArticle.getViews();
        }
        if(apArticle.getComment() != null){
            score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if(apArticle.getCollection() != null){
            score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }

        return score;
    }
}
