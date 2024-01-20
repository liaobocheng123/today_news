package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class NewsAuthDto extends PageRequestDto {

    /**
     * 文章标题
     */
    private String title;

    private Integer id;

    /**
     * 失败原因
     */
    private String msg;
}