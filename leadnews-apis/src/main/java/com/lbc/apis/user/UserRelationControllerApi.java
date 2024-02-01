package com.lbc.apis.user;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.UserRelationDto;

public interface UserRelationControllerApi {

    /**
     * 关注或取消关注
     * @param dto
     * @return
     */
    ResponseResult follow(UserRelationDto dto);
}