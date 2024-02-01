package com.lbc.admin.controller.v1;

import com.lbc.admin.service.AdChannelService;
import com.lbc.apis.admin.AdChannelControllerApi;
import com.lbc.model.admin.dtos.ChannelDto;
import com.lbc.model.admin.pojos.AdChannel;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
public class AdChannelController  implements AdChannelControllerApi {

    @Autowired
    private AdChannelService channelService;

    @Override
    @GetMapping("/channels")
    public ResponseResult findAll() {
        List<AdChannel> list = channelService.list();
        return ResponseResult.okResult(list);
    }

    @PostMapping("/list")
    @Override
    public ResponseResult findByNameAndPage(@RequestBody ChannelDto dto){
        return channelService.findByNameAndPage(dto);
    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AdChannel channel) {
        return channelService.insert(channel);
    }

    @Override
    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdChannel adChannel) {
        return channelService.update(adChannel);
    }

    @Override
    @GetMapping("/del/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return channelService.deleteById(id);
    }
}