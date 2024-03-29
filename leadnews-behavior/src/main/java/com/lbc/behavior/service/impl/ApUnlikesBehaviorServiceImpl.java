package com.lbc.behavior.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbc.behavior.mapper.ApUnlikesBehaviorMapper;
import com.lbc.behavior.service.ApBehaviorEntryService;
import com.lbc.behavior.service.ApUnlikesBehaviorService;
import com.lbc.model.behavior.dtos.UnLikesBehaviorDto;
import com.lbc.model.behavior.pojos.ApBehaviorEntry;
import com.lbc.model.behavior.pojos.ApUnlikesBehavior;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.model.user.pojos.ApUser;
import com.lbc.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * APP不喜欢行为表 服务实现类
 * </p>
 *
 * @author itheima
 */
@Slf4j
@Service
public class ApUnlikesBehaviorServiceImpl extends ServiceImpl<ApUnlikesBehaviorMapper, ApUnlikesBehavior> implements ApUnlikesBehaviorService {

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Override
    public ResponseResult unlike(UnLikesBehaviorDto dto) {
        //1.检查参数
        if(dto == null || dto.getArticleId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.查询行为实体
        ApUser user = AppThreadLocalUtils.getUser();
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryService.findByUserIdOrEquipmentId(user.getId(), dto.getEquipmentId());
        if(apBehaviorEntry == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //3.不喜欢
        ApUnlikesBehavior apUnlikesBehavior = getOne(Wrappers.<ApUnlikesBehavior>lambdaQuery().eq(ApUnlikesBehavior::getArticleId, dto.getArticleId()).eq(ApUnlikesBehavior::getEntryId, apBehaviorEntry.getId()));
        if(apUnlikesBehavior == null){
            apUnlikesBehavior = new ApUnlikesBehavior();
            apUnlikesBehavior.setArticleId(dto.getArticleId());
            apUnlikesBehavior.setEntryId(apBehaviorEntry.getId());
            apUnlikesBehavior.setType(Integer.valueOf(dto.getType()));
            apUnlikesBehavior.setCreatedTime(new Date());
            save(apUnlikesBehavior);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else{
            apUnlikesBehavior.setType(Integer.valueOf(dto.getType()));
            updateById(apUnlikesBehavior);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
    }
}