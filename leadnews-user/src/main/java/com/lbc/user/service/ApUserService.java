package com.lbc.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.UserDto;
import com.lbc.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser>{
    ResponseResult findByPhoneAndStatus(UserDto dto);

    ResponseResult updateUserStatus(Integer id);

}
