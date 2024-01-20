package com.heima.search.controller;

import com.heima.apis.search.ApAssociateWordsControllerApi;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.service.ApAssociateWordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 联想词表 前端控制器
 * </p>
 *
 * @author itheima
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/associate")
public class ApAssociateWordsController implements ApAssociateWordsControllerApi {

    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    @PostMapping("/search")
    @Override
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto) {
        return apAssociateWordsService.search(userSearchDto);
    }
}