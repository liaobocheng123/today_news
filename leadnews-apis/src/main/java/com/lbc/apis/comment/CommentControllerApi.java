package com.lbc.apis.comment;

import com.lbc.model.comment.dtos.CommentDto;
import com.lbc.model.comment.dtos.CommentLikeDto;
import com.lbc.model.comment.dtos.CommentSaveDto;
import com.lbc.model.common.dtos.ResponseResult;


public interface CommentControllerApi {

    /**
     * 保存评论
     * @param dto
     * @return
     */
    public ResponseResult saveComment(CommentSaveDto dto);

    /**
     * 点赞或取消点赞
     * @param dto
     * @return
     */
    public ResponseResult like(CommentLikeDto dto);

    /**
     * 查询文章评论列表
     * @param dto
     * @return
     */
    public ResponseResult findByArticleId(CommentDto dto);

}
