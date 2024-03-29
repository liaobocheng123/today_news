package com.lbc.apis.user;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.UserDto;
import com.lbc.model.user.pojos.ApUser;

public interface ApUserControllerApi {

    /**
     * 根据id查询app端用户信息
     * @param id
     * @return
     */
    ApUser findUserById(Integer id);

    ResponseResult findByPhoneAndStatus(UserDto dto);

    ResponseResult updateUserStatus(UserDto dto);
}
