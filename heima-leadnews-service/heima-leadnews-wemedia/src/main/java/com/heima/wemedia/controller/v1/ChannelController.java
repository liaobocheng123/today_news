package com.heima.wemedia.controller.v1;

import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.wemedia.service.WmChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "频道管理controller", tags = "频道管理controller")
@RestController // @Controller    @ResponseBody ==> json
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    private WmChannelService wmChannelService;

    @ApiOperation(value = "根据条件分页查询频道信息", notes = "条件 频道名称 状态 以ord升序查询频道")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "dto", dataType = "ChannelDTO", required = true)
    )
    @PostMapping("/list")
    public ResponseResult list(@RequestBody ChannelDto dto) {
        return wmChannelService.findByNameAndPage(dto);
    }

    @ApiOperation(value = "保存频道信息")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AdChannel channel) {
        return wmChannelService.insert(channel);
    }

    @ApiOperation(value = "修改频道信息")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody  AdChannel channel) {
        return wmChannelService.updateChannel(channel);
    }

    @ApiOperation(value = "删除频道信息")
    @GetMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id) {
        return wmChannelService.delete(id);
    }

    @ApiOperation("查询全部频道")
    @GetMapping("/channels")
    public ResponseResult findAll() {
        List<AdChannel> list = wmChannelService.list();
        return ResponseResult.okResult(list);
    }

    @ApiOperation("根据id查询频道")
    @GetMapping("/one/{id}")
    public ResponseResult findOne(@PathVariable Integer id) {
        return ResponseResult.okResult(wmChannelService.getById(id));
    }

}