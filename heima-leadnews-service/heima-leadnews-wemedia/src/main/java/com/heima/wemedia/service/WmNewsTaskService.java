package com.heima.wemedia.service;

import java.util.Date;

public interface WmNewsTaskService {
    void addNewsToTask(Integer id, Date publishTime);

    void scanNewsByTask();
}
