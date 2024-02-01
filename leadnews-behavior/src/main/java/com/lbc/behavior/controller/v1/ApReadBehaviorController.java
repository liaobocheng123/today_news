package com.lbc.behavior.controller.v1;

import com.lbc.apis.behavior.ApReadBehaviorControllerApi;
import com.lbc.behavior.service.ApReadBehaviorService;
import com.lbc.model.behavior.dtos.ReadBehaviorDto;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/read_behavior")
public class ApReadBehaviorController implements ApReadBehaviorControllerApi {

    @Autowired
    private ApReadBehaviorService apReadBehaviorService;

    @PostMapping
    @Override
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto dto) {
        return apReadBehaviorService.readBehavior(dto);
    }
}