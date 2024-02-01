package com.lbc.apis.admin;

import com.lbc.model.admin.dtos.AdUserDto;
import com.lbc.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
@Api(value = "登录管理", tags = "login", description = "登录管理API")
public interface LoginControllerApi {

    /**
     * admin登录功能
     * @param dto
     * @return
     */
    @ApiOperation("登录管理")
    public ResponseResult login(@RequestBody AdUserDto dto);
}