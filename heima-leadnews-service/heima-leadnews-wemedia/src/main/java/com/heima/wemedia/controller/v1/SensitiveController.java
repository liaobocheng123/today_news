package com.heima.wemedia.controller.v1;

import com.heima.wemedia.service.SensitiveService;
import com.heima.model.admin.dtos.SensitiveDTO;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/sensitive")
public class SensitiveController {

    @Resource
    private SensitiveService sensitiveService;

    @PostMapping("/list")
    public ResponseResult list(@RequestBody SensitiveDTO dto) {
        return sensitiveService.list(dto);
    }

    @PostMapping("/save")
    public ResponseResult insert(@RequestBody AdSensitive adSensitive) {
        return sensitiveService.insert(adSensitive);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdSensitive adSensitive) {
        return sensitiveService.update(adSensitive);
    }

    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id) {
        return sensitiveService.delete(id);
    }
}