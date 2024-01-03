package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.wemedia.mapper.ChannelMapper;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<ChannelMapper, AdChannel> implements WmChannelService {

    @Override
    public ResponseResult findAll() {
        List<AdChannel> list = list();
        log.info("list:{}",list.toString());
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult delete(Integer id) {
        if (id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        AdChannel adChannel = getById(id);
        if (adChannel == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        if (adChannel.getStatus()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"频道启动中，不能被删除");
        }
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult findByNameAndPage(ChannelDto dto) {
        //1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //检查分页
        dto.checkParam();

        //2.模糊查询+分页
        IPage page = new Page(dto.getPage(), dto.getSize());

        //频道名称模糊查询
        LambdaQueryWrapper<AdChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getName())) {
            lambdaQueryWrapper.like(AdChannel::getName, dto.getName());
        }

        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        log.info("responseResult:{}",responseResult);
        return responseResult;
    }

    @Override
    public ResponseResult insert(AdChannel adChannel) {
        if (adChannel == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        AdChannel channel = getOne(Wrappers.<AdChannel>lambdaQuery().eq(AdChannel::getName, adChannel.getName()));
        if (channel != null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"频道已存在，不可重复添加");
        }
        save(adChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult updateChannel(AdChannel adChannel) {
        if (adChannel.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        AdChannel oldChannel = getById(adChannel.getId());
        if (oldChannel == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"频道不存在");
        }
        String name = adChannel.getName();
        if (StringUtils.isNotBlank(name) && !name.equals(oldChannel.getName())){
            int count = count(Wrappers.<AdChannel>lambdaQuery().eq(AdChannel::getName, name));
            if (count > 0){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"名字已存在，不可重复");
            }
        }
        updateById(adChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}