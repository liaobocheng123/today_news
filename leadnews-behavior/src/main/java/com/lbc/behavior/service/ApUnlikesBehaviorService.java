package com.lbc.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.behavior.dtos.UnLikesBehaviorDto;
import com.lbc.model.behavior.pojos.ApUnlikesBehavior;
import com.lbc.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP不喜欢行为表 服务类
 * </p>
 *
 * @author itheima
 */
public interface ApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {

    ResponseResult unlike(UnLikesBehaviorDto dto);

}