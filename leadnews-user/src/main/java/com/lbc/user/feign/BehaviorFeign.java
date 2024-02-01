package com.lbc.user.feign;

import com.lbc.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("leadnews-behavior")
public interface BehaviorFeign {

    @PostMapping("/api/v1/behavior_entry/save")
    void insertOne(@RequestBody ApBehaviorEntry apBehaviorEntry);

}