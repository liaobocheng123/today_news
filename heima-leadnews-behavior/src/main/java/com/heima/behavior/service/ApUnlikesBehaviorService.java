package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import com.heima.model.common.dtos.ResponseResult;

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