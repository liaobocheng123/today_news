package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserDto extends PageRequestDto {


    private List<Map<String,Object>> where;
    private String name;
    private Integer id;

}