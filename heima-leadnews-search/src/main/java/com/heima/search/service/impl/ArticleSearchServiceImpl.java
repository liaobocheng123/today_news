package com.heima.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.search.feign.BehaviorFeign;
import com.heima.search.service.ApUserSearchService;
import com.heima.search.service.ArticleSearchService;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ArticleSearchServiceImpl implements ArticleSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ApUserSearchService apUserSearchService;

    @Autowired
    private BehaviorFeign behaviorFeign;

    /**
     * 获取行为实体
     * @param userSearchDto
     * @return
     */
    private ApBehaviorEntry getEntry(UserSearchDto userSearchDto) {
        ApUser user = AppThreadLocalUtils.getUser();
        return behaviorFeign.findByUserIdOrEntryId(user.getId(),userSearchDto.getEquipmentId());
    }

    /**
     * app端文章搜索
     * @param dto
     * @return
     */
    @Override
    public ResponseResult search(UserSearchDto dto) throws IOException {
        //1.检查参数
        if(dto == null || StringUtils.isBlank(dto.getSearchWords())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //只有在首页查询的时候才会保存
        if(dto.getFromIndex() == 0){
            ApBehaviorEntry apBehaviorEntry = getEntry(dto);
            if(apBehaviorEntry == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            apUserSearchService.insert(apBehaviorEntry.getId(),dto.getSearchWords());
        }

        //2.从es索引库中检索数据

        //构建搜索请求对象，需要指定索引库名称
        SearchRequest searchRequest = new SearchRequest("app_info_article");
        //条件构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //根据关键字分词查询--》title  content
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(dto.getSearchWords()).field("title").field("content").defaultOperator(Operator.OR);
        boolQueryBuilder.must(queryStringQueryBuilder);
        //查询小于minBehotTime的数据
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime").lt(dto.getMinBehotTime());
        boolQueryBuilder.filter(rangeQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);

        //按照发布时间倒序查询
        searchSourceBuilder.sort("publishTime", SortOrder.DESC);

        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(dto.getPageSize());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //3.封装返回

        List<Map<String,String>> articleList = new ArrayList<>();

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            Map<String, String> map = JSON.parseObject(sourceAsString, new TypeReference<Map<String, String>>(){});
            articleList.add(map);
        }
        return ResponseResult.okResult(articleList);
    }
}