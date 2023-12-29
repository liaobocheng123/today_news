package com.heima.schedule.feign;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.schedule.dtos.Task;
import com.heima.schedule.service.TaskService;
import com.lbc.apis.schedule.IScheduleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleClient implements IScheduleClient {
    @Autowired
    private TaskService taskService;
    @Override
    @PostMapping("/api/v1/task/add")
    public ResponseResult addTask(@RequestBody Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    @Override
    @GetMapping("/api/v1/task/{taskId}")
    public ResponseResult cancelTask(@PathVariable long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    @Override
    @GetMapping("/api/v1/task/{type}/{priority}")
    public ResponseResult poll(@PathVariable int type,@PathVariable int priority) {
        return ResponseResult.okResult(taskService.poll(type,priority));
    }
}
