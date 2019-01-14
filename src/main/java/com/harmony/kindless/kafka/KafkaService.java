package com.harmony.kindless.kafka;

/**
 * @author wuxii
 */
public interface KafkaService {

    void send(String topic, Object msg);

}
