package com.lbc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.admin.dtos.SensitiveDto;
import com.lbc.model.admin.pojos.AdSensitive;
import com.lbc.model.common.dtos.ResponseResult;

public interface AdSensitiveService extends IService<AdSensitive> {

    /**
     * 根据名称分页查询敏感词
     * @param dto
     * @return
     */
    public ResponseResult list(SensitiveDto dto);

    /**
     * 新增
     * @param adSensitive
     * @return
     */
    public ResponseResult insert(AdSensitive adSensitive);

    /**
     * 修改
     * @param adSensitive
     * @return
     */
    public ResponseResult update(AdSensitive adSensitive);

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    public ResponseResult deleteById(Integer id);
}