package com.heima.behavior.controller.v1;

import com.heima.apis.behavior.ApBehaviorEntryControllerApi;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/behavior_entry")
public class ApBehaviorEntryController implements ApBehaviorEntryControllerApi {

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @GetMapping("/one")
    @Override
    public ApBehaviorEntry findByUserIdOrEquipmentId(@RequestParam("userId") Integer userId,@RequestParam("equipmentId") Integer equipmentId) {
        return apBehaviorEntryService.findByUserIdOrEquipmentId(userId,equipmentId);
    }

    @Override
    @PostMapping("/save")
    public void insertOne(@RequestBody ApBehaviorEntry apBehaviorEntry) {
        apBehaviorEntryService.insert(apBehaviorEntry);
    }

}