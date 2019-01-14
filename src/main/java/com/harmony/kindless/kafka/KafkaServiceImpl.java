package com.harmony.kindless.kafka;

import com.alibaba.fastjson.JSON;
import com.harmony.kindless.messaging.events.PartitionEvent;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxii
 */
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final int partition;

    private AtomicInteger partitionCounter = new AtomicInteger();

    public KafkaServiceImpl(KafkaTemplate<String, String> kafkaTemplate, int partition) {
        this.kafkaTemplate = kafkaTemplate;
        this.partition = partition;
    }

    @Override
    public void send(String topic, Object msg) {
        int partitionIndex = partitionCounter.getAndIncrement() % partition;
        if (msg instanceof PartitionEvent) {
            ((PartitionEvent) msg).setPartition(partitionIndex);
        }
        kafkaTemplate.send(topic, partitionIndex, null, JSON.toJSONString(msg));
    }

}
