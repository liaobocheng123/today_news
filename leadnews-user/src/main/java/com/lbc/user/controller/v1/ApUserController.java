package com.lbc.user.controller.v1;

import com.lbc.apis.user.ApUserControllerApi;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.UserDto;
import com.lbc.model.user.pojos.ApUser;
import com.lbc.user.service.ApUserService;
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
