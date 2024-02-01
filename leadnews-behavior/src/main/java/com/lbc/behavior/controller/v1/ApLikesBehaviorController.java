package com.lbc.behavior.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lbc.apis.behavior.ApLikesBehaviorControllerApi;
import com.lbc.behavior.service.ApLikesBehaviorService;
import com.lbc.model.behavior.dtos.LikesBehaviorDto;
import com.lbc.model.behavior.pojos.ApLikesBehavior;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes_behavior")
public class ApLikesBehaviorController implements ApLikesBehaviorControllerApi {

    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    @PostMapping
    @Override
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return apLikesBehaviorService.like(dto);
    }

    @GetMapping("/one")
    @Override
    public ApLikesBehavior findLikeByArticleIdAndEntryId(@RequestParam("articleId") Long articleId, @RequestParam("entryId")Integer entryId, @RequestParam("type")Short type) {
        ApLikesBehavior apLikesBehavior = apLikesBehaviorService.getOne(Wrappers.<ApLikesBehavior>lambdaQuery()
                .eq(ApLikesBehavior::getArticleId, articleId).eq(ApLikesBehavior::getEntryId, entryId)
                .eq(ApLikesBehavior::getType, type));
        return apLikesBehavior;
    }
}