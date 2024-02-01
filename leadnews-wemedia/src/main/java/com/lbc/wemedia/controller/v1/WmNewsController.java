package com.lbc.wemedia.controller.v1;

import com.lbc.apis.wemedia.WmNewsControllerApi;
import com.lbc.model.admin.dtos.NewsAuthDto;
import com.lbc.model.common.dtos.PageResponseResult;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.model.wemedia.dtos.WmNewsDto;
import com.lbc.model.wemedia.dtos.WmNewsPageReqDto;
import com.lbc.model.wemedia.pojos.WmNews;
import com.lbc.model.wemedia.vo.WmNewsVo;
import com.lbc.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController implements WmNewsControllerApi {

    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("/list")
    @Override
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto wmNewsPageReqDto){
        return wmNewsService.findAll(wmNewsPageReqDto);
    }

    @GetMapping("/one/{id}")
    @Override
    public ResponseResult findWmNewsById(@PathVariable("id") Integer id) {
        return wmNewsService.findWmNewsById(id);
    }

    @GetMapping("/del_news/{id}")
    @Override
    public ResponseResult delNews(@PathVariable("id") Integer id) {
        return wmNewsService.delNews(id);
    }

    @PostMapping("/submit")
    @Override
    public ResponseResult summitNews(@RequestBody WmNewsDto wmNews) {
        if(wmNews.getStatus()== WmNews.Status.SUBMIT.getCode()){
            //提交文章
            return wmNewsService.saveNews(wmNews, WmNews.Status.SUBMIT.getCode());
        }else{
            //保存草稿
            return wmNewsService.saveNews(wmNews, WmNews.Status.NORMAL.getCode());
        }
    }

    @PostMapping("/down_or_up")
    @Override
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }

    @GetMapping("/findOne/{id}")
    @Override
    public WmNews findById(@PathVariable("id") Integer id) {
        return wmNewsService.getById(id);
    }

    @PostMapping("/update")
    @Override
    public ResponseResult updateWmNews(@RequestBody WmNews wmNews) {
        wmNewsService.updateById(wmNews);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    
    
    @GetMapping("/findRelease")
    @Override
    public List<Integer> findRelease() {
        return wmNewsService.findRelease();
    }

    @PostMapping("/findList")
    @Override
    public PageResponseResult findList(@RequestBody NewsAuthDto dto) {
        return wmNewsService.findListAndPage(dto);
    }

    @GetMapping("/find_news_vo/{id}")
    @Override
    public WmNewsVo findWmNewsVo(@PathVariable("id") Integer id) {
        return wmNewsService.findWmNewsVo(id);
    }
}