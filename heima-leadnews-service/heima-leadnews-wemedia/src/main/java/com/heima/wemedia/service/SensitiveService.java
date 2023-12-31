package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.SensitiveDTO;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmSensitive;

public interface SensitiveService extends IService<AdSensitive> {
    ResponseResult list(SensitiveDTO dto);

    ResponseResult insert(AdSensitive adSensitive);

    ResponseResult update(AdSensitive adSensitive);

    ResponseResult delete(Integer id);

}
