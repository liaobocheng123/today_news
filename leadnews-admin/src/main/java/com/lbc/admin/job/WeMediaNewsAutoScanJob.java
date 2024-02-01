package com.lbc.admin.job;

import com.lbc.admin.feign.WemediaFeign;
import com.lbc.admin.service.WemediaNewsAutoScanService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class WeMediaNewsAutoScanJob {

    @Autowired
    private WemediaNewsAutoScanService weMediaNewsAutoScanService;

    @Autowired
    private WemediaFeign wemediaFeign;

    /**
     * 每天0点执行一次
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("wemediaAutoScanJob")
    public ReturnT<String> hello(String param) throws Exception {
        log.info("自媒体文章审核调度任务开始执行....");
        System.out.println("自媒体文章审核调度任务开始执行....");
        List<Integer> releaseIdList = wemediaFeign.findRelease();
        if(null!=releaseIdList && !releaseIdList.isEmpty()){
            for (Integer id : releaseIdList) {
                weMediaNewsAutoScanService.autoScanByMediaNewsId(id);
            }
        }
        log.info("自媒体文章审核调度任务执行结束....");
        return ReturnT.SUCCESS;
    }

}