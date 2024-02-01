package com.lbc.model.comment.vo;

import com.lbc.model.comment.pojos.ApCommentRepay;
import lombok.Data;

@Data
public class ApCommentRepayVo extends ApCommentRepay {

    /**
     * 0 点赞
     * 1 未点赞
     */
    private Short operation;
}
