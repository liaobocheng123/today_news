package com.lbc.wemedia.controller.v1;

import com.lbc.apis.wemedia.LoginControllerApi;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.wemedia.dtos.WmUserDto;
import com.lbc.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping("/in")
    @Override
    public ResponseResult login(@RequestBody WmUserDto dto){
        
        return wmUserService.login(dto);
    }
}