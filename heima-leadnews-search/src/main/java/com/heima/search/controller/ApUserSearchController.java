package com.heima.search.controller;

import com.heima.apis.search.ApUserSearchControllerApi;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.service.ApUserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * APP用户搜索信息表 前端控制器
 * </p>
 *
 * @author qlp
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController implements ApUserSearchControllerApi {

    @Autowired
    private ApUserSearchService apUserSearchService;

    @PostMapping("/load")
    @Override
    public ResponseResult findUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return apUserSearchService.findUserSearch(userSearchDto);
    }

    @PostMapping("/del")
    @Override
    public ResponseResult delUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return apUserSearchService.delUserSearch(userSearchDto);
    }

}