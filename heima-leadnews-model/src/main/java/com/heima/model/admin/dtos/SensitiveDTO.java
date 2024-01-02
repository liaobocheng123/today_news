package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 敏感词数据传输对象DTO
 */
@Data
public class SensitiveDTO extends PageRequestDto {
    /**
     * 敏感词名称
     */
    private String name;
}
