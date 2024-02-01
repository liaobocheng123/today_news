package com.lbc.model.user.dtos;

import com.lbc.model.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserDto extends PageRequestDto {


    private List<Map<String,Object>> where;
    private String name;
    private Integer id;

}