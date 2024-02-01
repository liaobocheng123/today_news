package com.lbc.article.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lbc.article.service.ApArticleConfigService;
import com.lbc.common.constants.massage.WmNewsMessageConstants;
import com.lbc.model.article.pojos.ApArticleConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class ArticleIsDownListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void receiveMessage(ConsumerRecord<?,?> record){
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);
        System.out.println("kafka接收数据上下架完成");
        if(optional.isPresent()){
            String value = (String) record.value();
            Map map = JSON.parseObject(value, Map.class);
            System.out.println("数据上下架完成");
            apArticleConfigService.update(Wrappers.<ApArticleConfig>lambdaUpdate()
            .eq(ApArticleConfig::getArticleId,map.get("articleId")).set(ApArticleConfig::getIsDown,map.get("enable")));
        }
    }
}