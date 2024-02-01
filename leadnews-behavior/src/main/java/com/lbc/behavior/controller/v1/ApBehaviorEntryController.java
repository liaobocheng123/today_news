package com.lbc.behavior.controller.v1;

import com.lbc.apis.behavior.ApBehaviorEntryControllerApi;
import com.lbc.behavior.service.ApBehaviorEntryService;
import com.lbc.model.behavior.pojos.ApBehaviorEntry;
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