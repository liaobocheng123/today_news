package com.lbc.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.search.dtos.UserSearchDto;
import com.lbc.model.search.pojos.ApAssociateWords;

/**
 * <p>
 * 联想词表 服务类
 * </p>
 *
 * @author itheima
 */
public interface ApAssociateWordsService extends IService<ApAssociateWords> {

    /**
     联想词
     @param userSearchDto
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto);

    /**
     联想词 V2
     @param userSearchDto
     @return
     */
    ResponseResult searchV2(UserSearchDto userSearchDto);

}