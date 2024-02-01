package com.lbc.model.comment.vo;

import com.lbc.model.comment.pojos.ApComment;
import lombok.Data;


@Data
public class ApCommentVo extends ApComment {

    /**
     * 0 点赞
     * 1 未点赞
     */
    private Short operation;
}
