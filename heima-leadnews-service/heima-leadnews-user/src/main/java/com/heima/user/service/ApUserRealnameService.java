package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.AuthDTO;
import com.heima.model.user.pojos.ApUserRealname;

public interface ApUserRealnameService extends IService<ApUserRealname> {
    ResponseResult updateStatusById(AuthDTO dto, Short passAuth);

    ResponseResult loadListByStatus(AuthDTO authDTO);

}
