package com.heima.wemedia.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmNews;
import org.json.JSONException;

public interface WmNewsAutoScanService {

    void autoScanWmNews(Integer id) throws JSONException;

    public ResponseResult saveAppArticle(WmNews wmNews);
}
