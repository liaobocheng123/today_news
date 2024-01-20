package com.heima.behavior.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.ApFollowBehaviorService;
import com.heima.common.constants.massage.FollowBehaviorConstants;
import com.heima.model.behavior.dtos.FollowBehaviorDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FollowBehaviorListener {

    @Autowired
    private ApFollowBehaviorService apFollowBehaviorService;

    @KafkaListener(topics = FollowBehaviorConstants.FOLLOW_BEHAVIOR_TOPIC)
    public void receiverMessage(ConsumerRecord<?,?> record){
        //接收关注行为数据，保存
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            FollowBehaviorDto dto = JSON.parseObject(record.value().toString(), FollowBehaviorDto.class);
            System.out.println("kafka接收用户关注信息，保存关注数据");
            apFollowBehaviorService.saveFollowBehavior(dto);
        }
    }
}