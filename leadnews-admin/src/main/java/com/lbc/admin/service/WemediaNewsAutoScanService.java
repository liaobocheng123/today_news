package com.lbc.admin.service;

import com.lbc.model.admin.dtos.NewsAuthDto;
import com.lbc.model.common.dtos.PageResponseResult;
import com.lbc.model.common.dtos.ResponseResult;

public interface WemediaNewsAutoScanService {

    /**
     * 自媒体文章审核
     * @param id
     */
    public void autoScanByMediaNewsId(Integer id);


    /**
     * 根据文章标题分页查询自媒体文章列表
     * @param dto
     * @return
     */
    public PageResponseResult findNews(NewsAuthDto dto);

    /**
     * 根据文章id文章信息
     * @param id
     * @return
     */
    public ResponseResult findOne(Integer id);

    /**
     * 审核通过或驳回
     * @param type  0 为驳回  1位通过
     * @param dto
     * @return
     */
    public ResponseResult updateStatus(Integer type, NewsAuthDto dto);
}