package com.heima.model.mess;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ArticleVisitStreamMess {
    /**
     * 文章id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long articleId;
    /**
     * 阅读
     */
    private int view;
    /**
     * 收藏
     */
    private int collect;
    /**
     * 评论
     */
    private int comment;
    /**
     * 点赞
     */
    private int like;
}