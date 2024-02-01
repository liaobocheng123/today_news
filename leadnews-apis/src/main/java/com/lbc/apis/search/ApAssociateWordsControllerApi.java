package com.lbc.apis.search;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsControllerApi {

    /**
     联想词
     @param userSearchDto
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto);
}