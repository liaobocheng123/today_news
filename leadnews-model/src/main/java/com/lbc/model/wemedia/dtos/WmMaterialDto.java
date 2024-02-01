package com.lbc.model.wemedia.dtos;

import com.lbc.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmMaterialDto extends PageRequestDto {
    Short isCollected; //1 查询收藏的
    Short isCollection; //1 查询收藏的 这个才有用
}