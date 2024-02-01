package com.lbc.behavior.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lbc.apis.behavior.ApUnlikesBehaviorControllerApi;
import com.lbc.behavior.service.ApUnlikesBehaviorService;
import com.lbc.model.behavior.dtos.UnLikesBehaviorDto;
import com.lbc.model.behavior.pojos.ApUnlikesBehavior;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/unlike_behavior")
public class ApUnlikesBehaviorController implements ApUnlikesBehaviorControllerApi {

    @Autowired
    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    @PostMapping
    @Override
    public ResponseResult unlike(@RequestBody UnLikesBehaviorDto dto) {
        return apUnlikesBehaviorService.unlike(dto);
    }

    @GetMapping("/one")
    @Override
    public ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId) {
        return apUnlikesBehaviorService.getOne(Wrappers.<ApUnlikesBehavior>lambdaQuery().eq(ApUnlikesBehavior::getArticleId, articleId)
                .eq(ApUnlikesBehavior::getEntryId, entryId));
    }
}