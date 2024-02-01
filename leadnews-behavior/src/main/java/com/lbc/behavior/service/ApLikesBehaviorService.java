package com.lbc.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.behavior.dtos.LikesBehaviorDto;
import com.lbc.model.behavior.pojos.ApLikesBehavior;
import com.lbc.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP点赞行为表 服务类
 * </p>
 *
 * @author itheima
 */
public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {
    /**
     * 存储喜欢数据
     * @param dto
     * @return
     */
	public ResponseResult like(LikesBehaviorDto dto);

}