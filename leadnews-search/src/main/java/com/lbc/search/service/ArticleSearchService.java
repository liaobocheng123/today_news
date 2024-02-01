package com.lbc.search.service;

import com.lbc.model.search.dtos.UserSearchDto;
import com.lbc.model.common.dtos.ResponseResult;

import java.io.IOException;

public interface ArticleSearchService {

    /**
     ES文章分页搜索
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}