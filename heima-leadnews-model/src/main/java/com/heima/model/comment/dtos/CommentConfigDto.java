package com.heima.model.comment.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentConfigDto {

    /**
     * 文章id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long articleId;
    /**
     * 操作
     * 0  关闭评论
     * 1  开启评论
     */
    private Short operation;
}
