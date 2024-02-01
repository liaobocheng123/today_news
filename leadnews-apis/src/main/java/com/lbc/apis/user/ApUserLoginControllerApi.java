package com.lbc.apis.user;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.LoginDto;

public interface ApUserLoginControllerApi {

    /**
     * app端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

    ResponseResult register( LoginDto dto);
}