package com.lbc.user.controller.v1;

import com.lbc.apis.user.UserRelationControllerApi;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.UserRelationDto;
import com.lbc.user.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserRelationController implements UserRelationControllerApi {
    
    @Autowired
    private UserRelationService apUserRelationService;
    
    @Override
    @PostMapping("/user_follow")
    public ResponseResult follow(@RequestBody UserRelationDto dto){
        return apUserRelationService.follow(dto);
    }
}