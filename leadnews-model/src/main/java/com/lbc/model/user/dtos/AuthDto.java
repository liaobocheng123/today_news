package com.lbc.model.user.dtos;

import com.lbc.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class AuthDto extends PageRequestDto {

    /**
     * 认证的id
     */
    private Integer id;
    //驳回的信息
    private String msg;
    //状态
    private Short status;
}