package com.lbc.admin.controller.v1;

import com.lbc.admin.service.WemediaNewsAutoScanService;
import com.lbc.apis.admin.NewsAuthControllerApi;
import com.lbc.model.admin.dtos.NewsAuthDto;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news_auth")
public class NewsAuthController implements NewsAuthControllerApi {

    @Autowired
    private WemediaNewsAutoScanService wemediaNewsAutoScanService;

    @PostMapping("/list")
    @Override
    public ResponseResult findNews(@RequestBody NewsAuthDto dto){
        return wemediaNewsAutoScanService.findNews(dto);
    }

    @GetMapping("/one/{id}")
    @Override
    public ResponseResult findOne(@PathVariable("id") Integer id){
        return wemediaNewsAutoScanService.findOne(id);
    }

    @PostMapping("/auth_pass")
    @Override
    public ResponseResult authPass(@RequestBody NewsAuthDto dto){
        return wemediaNewsAutoScanService.updateStatus(1,dto);
    }

    @PostMapping("/auth_fail")
    @Override
    public ResponseResult authFail(@RequestBody NewsAuthDto dto){
        return wemediaNewsAutoScanService.updateStatus(0,dto);
    }
}