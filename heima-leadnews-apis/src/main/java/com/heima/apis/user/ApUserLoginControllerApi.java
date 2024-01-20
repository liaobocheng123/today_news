package com.heima.apis.user;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApUserLoginControllerApi {

    /**
     * app端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

    ResponseResult register( LoginDto dto);
}