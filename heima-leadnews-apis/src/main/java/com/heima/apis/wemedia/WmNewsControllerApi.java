package com.heima.apis.wemedia;

import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.vo.WmNewsVo;

import java.util.List;

/**
 * 自媒体文章接口
 */
public interface WmNewsControllerApi {

    /**
     * 分页带条件查询自媒体文章列表
     * @param wmNewsPageReqDto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto wmNewsPageReqDto);

    /**
     * 提交文章
     * @param wmNews
     * @return
     */
    ResponseResult summitNews(WmNewsDto wmNews);

    /**
     * 根据id获取文章信息
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
     * 根据id查询文章
     * @return
     */
    WmNews findById( Integer id);

    /**
     * 修改文章
     * @return
     */
    ResponseResult updateWmNews(WmNews wmNews);


    /**
     * 查询需要发布的文章id列表
     * @return
     */
    List<Integer> findRelease();


    /**
     * 查询文章列表
     * @param dto
     * @return
     */
    public PageResponseResult findList(NewsAuthDto dto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    public WmNewsVo findWmNewsVo(Integer id) ;
}