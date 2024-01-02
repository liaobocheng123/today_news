package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.wemedia.mapper.SensitiveMapper;
import com.heima.wemedia.service.SensitiveService;
import com.heima.model.admin.dtos.SensitiveDTO;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper,AdSensitive> implements SensitiveService {

    @Override
    public ResponseResult list(SensitiveDTO dto) {
        dto.checkParam();
        Page<AdSensitive> sensitivePage = new Page<>(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<AdSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getName())){
            lambdaQueryWrapper =  lambdaQueryWrapper.like(AdSensitive::getSensitives, dto.getName());
        }
        IPage<AdSensitive> page = page(sensitivePage,lambdaQueryWrapper);
        PageResponseResult pageResponseResult = new PageResponseResult(
                dto.getPage(),dto.getSize(), (int) page.getTotal(),page.getRecords());
        return pageResponseResult;
    }

    @Override
    public ResponseResult insert(AdSensitive adSensitive) {
        if (adSensitive == null || StringUtils.isBlank(adSensitive.getSensitives())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"不能为空");
        }
        //        2. 判断敏感词是否存在
        LambdaQueryWrapper<AdSensitive> adSensitiveLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adSensitiveLambdaQueryWrapper.eq(AdSensitive::getSensitives, adSensitive.getSensitives());
        int count = count(adSensitiveLambdaQueryWrapper);
        if (count > 0) { // 已存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"敏感词已存在");
        }
        adSensitive.setCreateTime(LocalDateTime.now());
        save(adSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(AdSensitive adSensitive) {
        if (adSensitive == null || StringUtils.isBlank(adSensitive.getSensitives())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"不能为空");
        }
        int count = count(Wrappers.<AdSensitive>lambdaQuery().eq(AdSensitive::getSensitives, adSensitive.getSensitives()));
        if (count > 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"敏感词已存在");
        }
        updateById(adSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult delete(Integer id) {
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
