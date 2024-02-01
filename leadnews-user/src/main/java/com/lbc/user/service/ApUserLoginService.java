package com.lbc.user.service;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.LoginDto;

public interface ApUserLoginService {

    /**
     * app端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

    ResponseResult register(LoginDto dto);

}