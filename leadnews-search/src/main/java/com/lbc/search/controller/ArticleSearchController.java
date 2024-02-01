package com.lbc.search.controller;

import com.lbc.apis.search.ArticleSearchControllerApi;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.search.dtos.UserSearchDto;
import com.lbc.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController implements ArticleSearchControllerApi {

    @Autowired
    private ArticleSearchService articleSearchService;

    @PostMapping("/search")
    @Override
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto) throws IOException {
        return articleSearchService.search(userSearchDto);
    }
}