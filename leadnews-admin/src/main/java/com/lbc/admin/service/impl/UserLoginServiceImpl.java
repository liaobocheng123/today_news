package com.lbc.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.lbc.admin.mapper.AdUserMapper;
import com.lbc.admin.service.UserLoginService;
import com.lbc.model.admin.dtos.AdUserDto;
import com.lbc.model.admin.pojos.AdUser;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.utils.common.AppJwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserLoginServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements UserLoginService {
    @Override
    public ResponseResult login(AdUserDto dto) {
        //1.参数校验
        if (StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "用户名或密码不能为空");
        }
        
        /*Wrapper wrapper = new QueryWrapper<AdUser>();
        ((QueryWrapper) wrapper).eq("name", dto.getName());*/
        
        List<AdUser> list = list(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName,dto.getName()));
        if (list != null && list.size() == 1) {
            //获得库中的实体
            AdUser adUser = list.get(0);
            String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + adUser.getSalt()).getBytes());
            if (adUser.getPassword().equals(pswd)) {
                Map<String, Object> map = Maps.newHashMap();
                adUser.setPassword("");
                adUser.setSalt("");
                map.put("token", AppJwtUtil.getToken(adUser.getId().longValue()));
                map.put("user", adUser);
                return ResponseResult.okResult(map);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
        }
    }
}