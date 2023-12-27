package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {
    @Autowired
    private FileStorageService fileStorageService;
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        if (multipartFile == null || multipartFile.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String originalFileName = multipartFile.getOriginalFilename();
        String postfix = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadHtmlFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId：{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传失败");
        }
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short) 0);
        wmMaterial.setType((short) 0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);
        log.info("保存数据到数据库");
        return ResponseResult.okResult(wmMaterial);
    }

    @Override
    public ResponseResult findList(WmMaterialDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(dto.getIsCollection() != null && dto.getIsCollection() == 1){
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection,dto.getIsCollection());
        }
        lambdaQueryWrapper.eq(WmMaterial::getUserId,WmThreadLocalUtil.getUser().getId());
        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);
        page = page(page, lambdaQueryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }
}
