package com.lbc.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.behavior.dtos.ReadBehaviorDto;
import com.lbc.model.behavior.pojos.ApReadBehavior;
import com.lbc.model.common.dtos.ResponseResult;

public interface ApReadBehaviorService extends IService<ApReadBehavior> {

    /**
     * 保存阅读行为
     * @param dto
     * @return
     */
    ResponseResult readBehavior(ReadBehaviorDto dto);
}