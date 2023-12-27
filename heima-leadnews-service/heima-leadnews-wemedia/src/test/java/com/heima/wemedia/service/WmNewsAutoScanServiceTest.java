package com.heima.wemedia.service;

import com.heima.wemedia.WemediaApplication;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class WmNewsAutoScanServiceTest {
    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;
    @Test
    public void autoScanWmNews() throws JSONException {
        wmNewsAutoScanService.autoScanWmNews(6236);
    }
}