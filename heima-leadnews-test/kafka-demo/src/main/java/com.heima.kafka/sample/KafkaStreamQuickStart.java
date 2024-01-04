package com.heima.kafka.sample;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 * 流式处理
 */
public class KafkaStreamQuickStart {
    public static void main(String[]args){
        //配置信息
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.130:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"streams-quickstart");

        //构建器
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsProcessor(streamsBuilder);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),properties);
        //开启流式计算
        kafkaStreams.start();

    }

    /**
     * 流式计算
     * @param streamsBuilder
     */
    private static void streamsProcessor(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("itcast-topic-input");
        stream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
            @Override
            public Iterable<String> apply(String value) {
                String[] valueArr = value.split(" ");
                return Arrays.asList(valueArr);
            }
        })
                .groupBy((key,value) -> value)
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                .count()
                .toStream().map((key,value) ->{
                    System.out.println("key:" + key + ",value:" + value);
                    return new KeyValue<>(key,value);
                })
                //发送消息
                .to("itcast-topic-out");
    }
}
