package com.heima.model.behavior.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class LikesBehaviorDto {


    // 文章、动态、评论等ID
    @JsonSerialize(using= ToStringSerializer.class)
    Long articleId;
    /**
     * 喜欢内容类型
     * 0 文章
     * 1 动态
     * 2 评论
     */
    Short type;

    /**
     * 喜欢操作方式
     * 0 点赞
     * 1 取消点赞
     */
    Short operation;
}
