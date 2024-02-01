package com.lbc.apis.behavior;

import com.lbc.model.behavior.dtos.ReadBehaviorDto;
import com.lbc.model.common.dtos.ResponseResult;

public interface ApReadBehaviorControllerApi {

    /**
     * 保存或更新阅读行为
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);
}