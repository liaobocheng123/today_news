package com.lbc.comment.controller.v1;

import com.lbc.apis.comment.CommentControllerApi;
import com.lbc.comment.service.CommentService;
import com.lbc.model.comment.dtos.CommentDto;
import com.lbc.model.comment.dtos.CommentLikeDto;
import com.lbc.model.comment.dtos.CommentSaveDto;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController implements CommentControllerApi {

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    @Override
    public ResponseResult saveComment(@RequestBody CommentSaveDto dto) {
        return commentService.saveComment(dto);
    }

    /**
     * 点赞或取消点赞
     * @param dto
     * @return
     */
    @PostMapping("/like")
    @Override
    public ResponseResult like(@RequestBody CommentLikeDto dto) {
        return commentService.like(dto);
    }

    /**
     * 查询文章评论列表
     *
     * @param dto
     * @return
     */
    @PostMapping("/load")
    @Override
    public ResponseResult findByArticleId(@RequestBody CommentDto dto) {
        return commentService.findByArticleId(dto);
    }
}
