package com.lbc.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.search.dtos.UserSearchDto;
import com.lbc.model.search.pojos.ApUserSearch;

/**
 * <p>
 * APP用户搜索信息表 服务类
 * </p>
 *
 * @author qlp
 */
public interface ApUserSearchService extends IService<ApUserSearch> {

    /**
     查询搜索历史
     @param userSearchDto
     @return
     */
    ResponseResult findUserSearch(UserSearchDto userSearchDto);

    /**
     删除搜索历史
     @param userSearchDto
     @return
     */
    ResponseResult delUserSearch(UserSearchDto userSearchDto);

    /**
     插入搜索记录
     @param
     @return
     */
    void insert(Integer entryId,String searchWords);

}