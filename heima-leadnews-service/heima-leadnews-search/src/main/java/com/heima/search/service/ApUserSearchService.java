package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;

public interface ApUserSearchService {
    void insert(String keyword,Integer userId);

    ResponseResult findUserSearch();

    ResponseResult delUserSearch(HistorySearchDto dto);

}
