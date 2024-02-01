package com.lbc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.admin.dtos.AdUserDto;
import com.lbc.model.admin.pojos.AdUser;
import com.lbc.model.common.dtos.ResponseResult;

public interface UserLoginService extends IService<AdUser> {
    /**
     * 登录功能
     * @param dto
     * @return
     */
    ResponseResult login(AdUserDto dto);
}