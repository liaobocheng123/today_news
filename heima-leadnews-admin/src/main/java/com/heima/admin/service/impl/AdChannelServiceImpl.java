package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdChannelMapper;
import com.heima.admin.service.AdChannelService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdChannelServiceImpl extends ServiceImpl<AdChannelMapper, AdChannel> implements AdChannelService {
    

    @Override
    public ResponseResult findByNameAndPage(ChannelDto dto) {

        //1.参数检测
        if(dto==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();

        //2.安装名称模糊分页查询
        Page page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<AdChannel> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(dto.getName())){
            lambdaQueryWrapper.like(AdChannel::getName,dto.getName());
        }
        IPage result = page(page, lambdaQueryWrapper);

        //3.结果封装
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)result.getTotal());
        responseResult.setData(result.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult insert(AdChannel channel) {
        //1.参数检测
        if(channel==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        
        //保存
        channel.setCreatedTime(new Date());
        save(channel);
        
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(AdChannel adChannel) {
        //1.参数检测
        if(adChannel==null || null==adChannel.getId()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //修改
        updateById(adChannel);
        
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Integer id) {
        //1.参数检测
        if(id==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.判断是否存在当前id的信息
        AdChannel byId = getById(id);
        if(byId==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //3.判断当前id的信息是否有效，只有无效的才能逻辑删除
        if(byId.getStatus()){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"频道现在有效，请设置为无效再删除");
        }
        
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}