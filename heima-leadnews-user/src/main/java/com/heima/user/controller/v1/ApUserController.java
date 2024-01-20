package com.heima.user.controller.v1;

import com.heima.apis.user.ApUserControllerApi;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class ApUserController implements ApUserControllerApi {

    @Autowired
    private ApUserService apUserService;

    @GetMapping("/{id}")
    @Override
    public ApUser findUserById(@PathVariable("id") Integer id) {
        return apUserService.getById(id);
    }

    @PostMapping("/list")
    @Override
    public ResponseResult findByPhoneAndStatus(@RequestBody UserDto dto){
        return apUserService.findByPhoneAndStatus(dto);
    }

    @PostMapping("/updateUserStatus")
    @Override
    public ResponseResult updateUserStatus(@RequestBody UserDto dto){
        return apUserService.updateUserStatus(dto.getId());
    }
}
