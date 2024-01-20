package com.heima.apis.user;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserDto;
import com.heima.model.user.pojos.ApUser;
import org.springframework.web.bind.annotation.RequestBody;

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
