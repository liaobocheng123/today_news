package com.lbc.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbc.model.common.dtos.PageResponseResult;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.model.user.dtos.UserDto;
import com.lbc.model.user.pojos.ApUser;
import com.lbc.user.mapper.ApUserMapper;
import com.lbc.user.service.ApUserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {


    @Override
    public ResponseResult findByPhoneAndStatus(UserDto dto) {
        //1.参数检测
        if(dto==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();

        String phoneLike = "";
        if(dto.getWhere()!=null && dto.getWhere().size() > 0){
            for (Map<String, Object> stringObjectMap : dto.getWhere()) {
                if(stringObjectMap.get("filed").equals("name")){
                    phoneLike = (String) stringObjectMap.get("value");
                }
            }
        }

        //2.按照手机模糊分页查询
        Page page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<ApUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(ApUser::getPhone,phoneLike);
        IPage result = page(page, lambdaQueryWrapper);

        //3.结果封装
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)result.getTotal());
        responseResult.setData(result.getRecords());
        return responseResult;
    }


    @Override
    public ResponseResult updateUserStatus(Integer id) {
        if(id==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApUser apUser = getById(id);
        if(apUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if(apUser.getStatus()){
            //要改成0
            apUser.setStatus(false);
            updateById(apUser);
        }else {
            //要改成1
            apUser.setStatus(true);
            updateById(apUser);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
