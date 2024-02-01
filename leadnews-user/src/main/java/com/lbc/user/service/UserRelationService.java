package com.lbc.user.service;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.UserRelationDto;

public interface UserRelationService {

    /**
     * 用户关注/取消关注
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);
}