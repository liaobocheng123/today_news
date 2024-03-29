package com.lbc.admin.controller.v1;

import com.lbc.admin.service.UserLoginService;
import com.lbc.apis.admin.LoginControllerApi;
import com.lbc.model.admin.dtos.AdUserDto;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private UserLoginService userLoginService ;

    @Override
    @PostMapping("/in")
    public ResponseResult login(@RequestBody AdUserDto dto){
        return userLoginService.login(dto);
    }
}