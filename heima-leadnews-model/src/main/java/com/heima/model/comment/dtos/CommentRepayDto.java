package com.heima.model.comment.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class CommentRepayDto {

    /**
     * 评论id
     */
    private String commentId;

    private Integer size;

    // 最小时间
    private Date minDate;
}