package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.behavior.pojos.ApBehaviorEntry;

public interface ApBehaviorEntryService extends IService<ApBehaviorEntry> {

    /**
     * 根据用户或设备查询行为实体
     * @param userId
     * @param equipmentId
     * @return
     */
    ApBehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId);

    void insert(ApBehaviorEntry apBehaviorEntry);

}