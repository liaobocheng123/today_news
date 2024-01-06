package com.lbc.comment.service.impl;

import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.lbc.comment.service.CommentRepayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CommentRepayServiceImpl implements CommentRepayService {
    @Override
    public ResponseResult loadCommentRepay(CommentRepayDto dto) {
        return null;
    }

    @Override
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto) {
        return null;
    }

    @Override
    public ResponseResult saveCommentRepayLike(CommentRepayLikeDto dto) {
        return null;
    }
}
