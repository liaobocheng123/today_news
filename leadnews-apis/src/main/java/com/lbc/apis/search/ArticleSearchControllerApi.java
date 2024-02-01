package com.lbc.apis.search;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.search.dtos.UserSearchDto;

import java.io.IOException;

public interface ArticleSearchControllerApi {

    /**
     *  搜索文章
     * @param userSearchDto
     * @return
     */
    public ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}