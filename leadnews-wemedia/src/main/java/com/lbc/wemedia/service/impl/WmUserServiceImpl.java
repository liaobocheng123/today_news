package com.lbc.wemedia.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.model.wemedia.dtos.WmUserDto;
import com.lbc.model.wemedia.pojos.WmUser;
import com.lbc.utils.common.AppJwtUtil;
import com.lbc.wemedia.mapper.WmUserMapper;
import com.lbc.wemedia.service.WmUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {
    @Override
    public ResponseResult login(WmUserDto dto) {
        //1.检查参数
        if (StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "用户名或密码错误");
        }
        //2.查询数据库中的用户信息
        List<WmUser> list = list(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, dto.getName()));
        if(list != null && list.size() ==1){
            WmUser wmUser = list.get(0);
            //3.比对密码
            String pswd = DigestUtils.md5DigestAsHex((dto.getPassword()+wmUser.getSalt()).getBytes());
            if(wmUser.getPassword().equals(pswd)){
                //4.返回数据jwt
                Map<String,Object> map = new HashMap<>();
                map.put("token", AppJwtUtil.getToken(wmUser.getId().longValue()));
                wmUser.setPassword("");
                wmUser.setSalt("");
                map.put("user",wmUser);
                return ResponseResult.okResult(map);
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }
    }
}