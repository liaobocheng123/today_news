package com.lbc.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lbc.model.behavior.pojos.ApBehaviorEntry;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.model.user.dtos.LoginDto;
import com.lbc.model.user.pojos.ApUser;
import com.lbc.model.user.pojos.ApUserRealname;
import com.lbc.user.feign.BehaviorFeign;
import com.lbc.user.mapper.ApUserMapper;
import com.lbc.user.mapper.ApUserRealnameMapper;
import com.lbc.user.service.ApUserLoginService;
import com.lbc.utils.common.AppJwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApUserLoginServiceImpl implements ApUserLoginService {

    @Autowired
    ApUserMapper apUserMapper;

    @Autowired
    BehaviorFeign behaviorFeign;

    @Autowired
    ApUserRealnameMapper apUserRealnameMapper;

    @Override
    public ResponseResult login(LoginDto dto) {
        //1.校验参数
        if(dto.getEquipmentId() == null && (StringUtils.isEmpty(dto.getPassword())&&StringUtils.isEmpty(dto.getPassword()))){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.手机号+密码登录
        if(!StringUtils.isEmpty(dto.getPassword())&&!StringUtils.isEmpty(dto.getPassword())){
            //用户登录
            ApUser dbUser = apUserMapper.selectOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (dbUser!=null) {
                String pswd = DigestUtils.md5DigestAsHex((dto.getPassword()+dbUser.getSalt()).getBytes());
                if (dbUser.getPassword().equals(pswd)) {
                    //如果状态为禁用也不能登录
                    if(dbUser.getStatus()){
                        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
                    }
                    Map<String, Object> map = new HashMap<>();
                    dbUser.setPassword("");
                    dbUser.setSalt("");
                    map.put("token", AppJwtUtil.getToken(dbUser.getId().longValue()));
                    map.put("user", dbUser);
                    return ResponseResult.okResult(map);
                } else {
                    return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
                }
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
            }
        }else {
            //3.设备登录
            if(dto.getEquipmentId() == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("token",AppJwtUtil.getToken(0l));
            return ResponseResult.okResult(map);

        }

    }

    @Override
    public ResponseResult register(LoginDto dto) {
        if(dto.getName() == null || "".equals(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(dto.getPhone() == null || "".equals(dto.getPhone())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //不允许手机号重复
        Map<String,Object> map = new HashMap<>();
        map.put("phone",dto.getPhone());
        List<ApUser> apUsers = apUserMapper.selectByMap(map);
        if (apUsers != null && apUsers.size() > 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        String passwordM = dto.getPasswordM();//注册时输入的密码明文
        String salt = "123456";//默认salt
        //加密
        String pswd = DigestUtils.md5DigestAsHex( (passwordM + salt ).getBytes());


        ApUser apUser = new ApUser();
        apUser.setSalt(salt);
        apUser.setName(dto.getName());
        apUser.setPassword(pswd);
        apUser.setPhone(dto.getPhone());
        //apUser.setImage();
        apUser.setSex(!"男".equals(dto.getSex()));
        apUser.setStatus(false);//默认正常用户
        apUser.setFlag((short) 0);
        apUser.setCreatedTime(new Date());

        apUserMapper.insert(apUser);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("phone",dto.getPhone());
        map2.put("status",0);
        List<ApUser> apUsers2 = apUserMapper.selectByMap(map2);


        //增加行为实体  ap_behavior_entry
        ApBehaviorEntry apBehaviorEntry = new ApBehaviorEntry();
        apBehaviorEntry.setType((short)1);
        apBehaviorEntry.setEntryId(apUsers2.get(0).getId());
        apBehaviorEntry.setCreatedTime(new Date());
        behaviorFeign.insertOne(apBehaviorEntry);


        if("是".equals(dto.getFlags())){
            //发起用户审核 自媒体用户  新增ap_user_realname
            ApUserRealname in = new ApUserRealname();
            in.setUserId(apUsers2.get(0).getId());
            in.setName(dto.getName());
            in.setIdno("512335455602781279");//默认身份证号
            in.setStatus((short)1);
            in.setCreatedTime(new Date());
            in.setSubmitedTime(new Date());

            apUserRealnameMapper.insert(in);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}