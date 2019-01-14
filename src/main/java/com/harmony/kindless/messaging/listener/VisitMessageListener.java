package com.harmony.kindless.messaging.listener;

import com.harmony.kindless.messaging.TopicNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxii
 */
@Component
public class VisitMessageListener {

    private static final Object lockObject = new Object();

    private static final Logger log = LoggerFactory.getLogger(VisitMessageListener.class);

    private static long startedAt;
    private static final AtomicInteger counter = new AtomicInteger();

    @KafkaListener(topicPattern = TopicNames.VISIT_EVENT, topicPartitions = {
            @TopicPartition(topic = TopicNames.VISIT_EVENT, partitions = {"0", "1"})
    })
    public void handle1(String msg) {
        doHandle(msg);
    }

    @KafkaListener(topicPattern = TopicNames.VISIT_EVENT, topicPartitions = {
            @TopicPartition(topic = TopicNames.VISIT_EVENT, partitions = {"2", "3"})
    })
    public void handle2(String msg) {
        doHandle(msg);
    }

    @KafkaListener(topicPattern = TopicNames.VISIT_EVENT, topicPartitions = {
            @TopicPartition(topic = TopicNames.VISIT_EVENT, partitions = {"4", "5"})
    })
    public void handle3(String msg) {
        doHandle(msg);
    }

    @KafkaListener(topicPattern = TopicNames.VISIT_EVENT, topicPartitions = {
            @TopicPartition(topic = TopicNames.VISIT_EVENT, partitions = {"6", "7"})
    })
    public void handle4(String msg) {
        doHandle(msg);
    }

    @KafkaListener(topicPattern = TopicNames.VISIT_EVENT, topicPartitions = {
            @TopicPartition(topic = TopicNames.VISIT_EVENT, partitions = {"8", "9"})
    })
    public void handle5(String msg) {
        doHandle(msg);
    }

    private void doHandle(String msg) {
        start();
        int currentIndex = 0;
        log.info("处理测试消息, {}", msg);
        try {
            Thread.sleep(1000);
            currentIndex = counter.getAndIncrement();
        } catch (InterruptedException e) {
        }
        double costSeconds = 1.0 * TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startedAt);
        log.info("消息处理完成. times/cost = {}", currentIndex / costSeconds);
    }

    private static void start() {
        if (startedAt == 0) {
            synchronized (lockObject) {
                log.info("开始处理消息");
                startedAt = System.currentTimeMillis();
            }
        }
    }

}
