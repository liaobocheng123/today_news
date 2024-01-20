package com.heima.model.user.dtos;

import lombok.Data;

@Data
public class LoginDto {

    //设备id
    private Integer equipmentId;

    //手机号
    private String phone;

    //密码
    private String password;


    private String flags; //注册输入
    private String name;//注册输入
    private String passwordM;//注册输入
    private String sex;//注册输入
}