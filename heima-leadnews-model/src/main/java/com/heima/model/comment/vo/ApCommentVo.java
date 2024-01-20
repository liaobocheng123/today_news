package com.heima.model.comment.vo;

import com.heima.model.comment.pojos.ApComment;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class ApCommentVo extends ApComment {

    /**
     * 0 点赞
     * 1 未点赞
     */
    private Short operation;
}
