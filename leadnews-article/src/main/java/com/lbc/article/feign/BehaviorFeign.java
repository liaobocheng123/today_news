package com.lbc.article.feign;

import com.lbc.model.behavior.pojos.ApBehaviorEntry;
import com.lbc.model.behavior.pojos.ApLikesBehavior;
import com.lbc.model.behavior.pojos.ApUnlikesBehavior;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("leadnews-behavior")
public interface BehaviorFeign {
    
    @GetMapping("/api/v1/behavior_entry/one")
    public ApBehaviorEntry findByUserIdOrEntryId(@RequestParam("userId") Integer userId, @RequestParam("equipmentId") Integer equipmentId);

    @GetMapping("/api/v1/unlike_behavior/one")
    ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId);

    @GetMapping("/api/v1/likes_behavior/one")
    ApLikesBehavior findLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId,@RequestParam("articleId") Long articleId,@RequestParam("type") short type);
}