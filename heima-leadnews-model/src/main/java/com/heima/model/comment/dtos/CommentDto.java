package com.heima.model.comment.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    /**
     * 文章id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long articleId;

    // 最小时间
    private Date minDate;

    //是否是首页
    private Short index;

}