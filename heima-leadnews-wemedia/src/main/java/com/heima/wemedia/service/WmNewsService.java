package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.vo.WmNewsVo;

import java.util.List;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询所有自媒体文章
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto wmNewsPageReqDto);

    /**
     * 自媒体文章发布
     * @param dto
     * @param isSubmit  是否为提交 1为提交 0为草稿
     * @return
     */
    ResponseResult saveNews(WmNewsDto dto, Short isSubmit);

    /**
     * 根据文章id查询文章
     * @return
     */
    ResponseResult findWmNewsById(Integer id);

    /**
     * 删除文章
     * @return
     */
    ResponseResult delNews(Integer id);

    /**
     * 上下架
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 查询需要发布的文章id列表
     * @return
     */
    List<Integer> findRelease();

    /**
     * 分页查询文章信息
     * @param dto
     * @return
     */
    public PageResponseResult findListAndPage(NewsAuthDto dto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    WmNewsVo findWmNewsVo(Integer id);
}