package com.lbc.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.wemedia.dtos.WmUserDto;
import com.lbc.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {
    /**
     * 登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmUserDto dto);
}