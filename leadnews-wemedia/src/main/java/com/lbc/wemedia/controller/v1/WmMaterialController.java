package com.lbc.wemedia.controller.v1;

import com.lbc.apis.wemedia.WmMaterialControllerApi;
import com.lbc.common.constants.media.WemediaContans;
import com.lbc.model.common.dtos.ResponseResult;

import com.lbc.model.wemedia.dtos.WmMaterialDto;
import com.lbc.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController implements WmMaterialControllerApi {

    @GetMapping("/cancel_collect/{id}")
    @Override
    public ResponseResult cancleCollectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, WemediaContans.CANCEL_COLLECT_MATERIAL);
    }

    @GetMapping("/collect/{id}")
    @Override
    public ResponseResult collectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, WemediaContans.COLLECT_MATERIAL);
    }

    @Autowired
    private WmMaterialService wmMaterialService;

    @GetMapping("/del_picture/{id}")
    @Override
    public ResponseResult delPicture(@PathVariable("id") Integer id) {
        return wmMaterialService.delPicture(id);
    }

    @PostMapping("/upload_picture")
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @Override
    @RequestMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto) {
        return wmMaterialService.findList(dto);
    }
}