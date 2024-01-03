package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;

public interface WmChannelService extends IService<AdChannel> {
    ResponseResult findAll();

    ResponseResult delete(Integer id);

    ResponseResult findByNameAndPage(ChannelDto dto);

    ResponseResult insert(AdChannel adChannel);

    ResponseResult updateChannel(AdChannel adChannel);

}