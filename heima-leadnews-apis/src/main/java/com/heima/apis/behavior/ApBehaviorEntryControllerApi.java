package com.heima.apis.behavior;

import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApBehaviorEntryControllerApi {

    /**
     * 查询行为实体
     * @param userId
     * @param equipmentId
     * @return
     */
    public ApBehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId);

    void insertOne(@RequestBody ApBehaviorEntry apBehaviorEntry);
}