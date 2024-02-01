package com.lbc.apis.behavior;

import com.lbc.model.behavior.dtos.UnLikesBehaviorDto;
import com.lbc.model.behavior.pojos.ApUnlikesBehavior;
import com.lbc.model.common.dtos.ResponseResult;

public interface ApUnlikesBehaviorControllerApi {

    /**
     * 根据行为实体id和文章id查询不喜欢行为
     * @param entryId
     * @param articleId
     * @return
     */
    public ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(Integer entryId,Long articleId);

    public ResponseResult unlike(UnLikesBehaviorDto dto);
}