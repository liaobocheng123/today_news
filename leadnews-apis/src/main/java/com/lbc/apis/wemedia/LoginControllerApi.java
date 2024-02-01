package com.lbc.apis.wemedia;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.wemedia.dtos.WmUserDto;

public interface LoginControllerApi {

    /**
     * 自媒体登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmUserDto dto);
}