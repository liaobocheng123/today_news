package com.lbc.apis.schedule;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.schedule.dtos.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("leadnews-schedule")

public interface IScheduleClient {
    @PostMapping("/api/v1/task/add")
    ResponseResult addTask(@RequestBody Task task);

    @GetMapping("/api/v1/task/{taskId}")
    ResponseResult cancelTask(@PathVariable long taskId);

    @GetMapping("/api/v1/task/{type}/{priority}")
    ResponseResult poll(@PathVariable int type,@PathVariable int priority);
}
