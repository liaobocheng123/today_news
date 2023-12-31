package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.AuthDTO;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import org.json.JSONException;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 条件查询文章列表
     * @param dto
     * @return
     */
    ResponseResult findList(AuthDTO dto);

    /**
     * 发布修改文章或保存为草稿
     * @param dto
     * @return
     */
    ResponseResult submitNews(WmNewsDto dto) throws JSONException;

    ResponseResult downOrUp(WmNewsDto dto);

    ResponseResult findOne(Integer id);

    ResponseResult findWmNewsVo(Integer id);

    ResponseResult updateStatus(Short wmNewsAuthPass, AuthDTO dto);

    ResponseResult findAll(WmNewsPageReqDto dto);

}
