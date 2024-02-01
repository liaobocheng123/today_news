package com.lbc.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.behavior.dtos.FollowBehaviorDto;
import com.lbc.model.behavior.pojos.ApFollowBehavior;
import com.lbc.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP关注行为表 服务类
 * </p>
 *
 * @author itheima
 */
public interface ApFollowBehaviorService extends IService<ApFollowBehavior> {

    /**
     * 存储关注数据
     * @param dto
     * @return
     */
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto);
}