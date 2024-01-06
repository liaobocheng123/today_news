package com.heima.model.comment.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentSaveDto {

    /**
     * 文章id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;
}