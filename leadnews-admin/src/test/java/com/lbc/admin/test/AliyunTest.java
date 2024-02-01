package com.lbc.admin.test;

import com.lbc.admin.AdminApplication;
import com.lbc.common.aliyun.GreeTextScan;
import com.lbc.common.aliyun.GreenImageScan;
import com.lbc.common.fastdfs.FastDFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author QLP
 * @version 1.0
 * @date 2021/8/28 12:28
 */
@SpringBootTest(classes = AdminApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {
    
    @Autowired
    private GreeTextScan greeTextScan;
    
    @Autowired
    private GreenImageScan greenImageScan;
    
    @Autowired
    private FastDFSClient fastDFSClient;
    
    @Test
    public void test() throws Exception {
        Map map = greeTextScan.greeTextScan("我是一个文本，涉黄,测试");
        System.out.println(map);
        
    }

    @Test
    public void test2() throws Exception {
        byte[] group1s = fastDFSClient.download("group1", "M00/00/00/wKjIgmEfFPOAY_h6AAeont8Bhx8820.png");//group1/M00/00/00/wKjIgmEfFPOAY_h6AAeont8Bhx8820.png
        List<byte[]> a=new ArrayList<>();
        a.add(group1s);
        Map map = greenImageScan.imageScan(a);
        System.out.println(map);

    }
}
