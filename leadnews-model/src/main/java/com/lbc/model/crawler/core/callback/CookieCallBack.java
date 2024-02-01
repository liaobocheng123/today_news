package com.lbc.model.crawler.core.callback;


import com.lbc.model.crawler.core.cookie.CrawlerCookie;

public interface CookieCallBack {
    /**
     * 获取CookieMap
     *
     * @return
     */
    public CrawlerCookie getCookieEntity(String url);


}
