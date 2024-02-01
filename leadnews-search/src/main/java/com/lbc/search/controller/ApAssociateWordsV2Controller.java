package com.lbc.search.controller;

import com.lbc.apis.search.ApAssociateWordsControllerApi;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.search.dtos.UserSearchDto;
import com.lbc.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/associate")
public class ApAssociateWordsV2Controller implements ApAssociateWordsControllerApi {

    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    @PostMapping("/search")
    @Override
    public ResponseResult search(@RequestBody UserSearchDto dto) {
        return apAssociateWordsService.searchV2(dto);
    }
}