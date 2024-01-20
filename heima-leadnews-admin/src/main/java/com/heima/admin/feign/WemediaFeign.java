package com.heima.admin.feign;

import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.model.wemedia.vo.WmNewsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("leadnews-wemedia")//nacos中的其他模块名称
public interface WemediaFeign {

    @GetMapping("/api/v1/news/findOne/{id}")
    WmNews findById(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/news/update")
    ResponseResult updateWmNews(WmNews wmNews);

    @GetMapping("/api/v1/user/findOne/{id}")
    WmUser findWmUserById(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/news/findRelease")
    List<Integer> findRelease();

    @PostMapping("/api/v1/news/findList/")
    public PageResponseResult findList(NewsAuthDto dto);

    @GetMapping("/api/v1/news/find_news_vo/{id}")
    public WmNewsVo findWmNewsVo(@PathVariable("id") Integer id);
}