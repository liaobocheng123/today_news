package com.lbc.apis.admin;

import com.lbc.model.admin.dtos.NewsAuthDto;
import com.lbc.model.common.dtos.ResponseResult;

public interface NewsAuthControllerApi {

    /**
     * 查询自媒体文章列表
     * @param dto
     * @return
     */
    public ResponseResult findNews(NewsAuthDto dto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    public ResponseResult findOne(Integer id);

    /**
     * 文章审核成功
     * @param dto
     * @return
     */
    public ResponseResult authPass(NewsAuthDto dto);

    /**
     * 文章审核失败
     * @param dto
     * @return
     */
    public ResponseResult authFail(NewsAuthDto dto);
}