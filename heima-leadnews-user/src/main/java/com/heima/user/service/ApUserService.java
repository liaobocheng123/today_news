package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserDto;
import com.heima.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser>{
    ResponseResult findByPhoneAndStatus(UserDto dto);

    ResponseResult updateUserStatus(Integer id);

}
