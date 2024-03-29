package com.lbc.user.controller.v1;

import com.lbc.apis.user.ApUserLoginControllerApi;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.LoginDto;
import com.lbc.user.service.ApUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class ApUserLoginController implements ApUserLoginControllerApi {

    @Autowired
    private ApUserLoginService apUserLoginService;

    @PostMapping("/login_auth")
    @Override
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserLoginService.login(dto);
    }

    @PostMapping("/user_register")
    @Override
    public ResponseResult register(@RequestBody LoginDto dto) {
        return apUserLoginService.register(dto);
    }
}