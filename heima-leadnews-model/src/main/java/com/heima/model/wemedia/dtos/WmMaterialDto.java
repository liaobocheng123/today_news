package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmMaterialDto extends PageRequestDto {
    Short isCollected; //1 查询收藏的
    Short isCollection; //1 查询收藏的 这个才有用
}