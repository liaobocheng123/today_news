package com.lbc.model.comment.dtos;

import com.lbc.model.common.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    // 最小时间
    private Date minDate;

    //是否是首页
    private Short index;

}