package com.harmony.kindless.test;

import com.harmony.kindless.kafka.KafkaService;
import com.harmony.kindless.messaging.TopicNames;
import com.harmony.kindless.messaging.events.VisitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class TestService {

    @Autowired
    private KafkaService kafkaService;

    public void kafka() {
        VisitEvent event = new VisitEvent();
        event.setUserId((long) (Math.random() * 10));
        event.setVisitorId((long) (Math.random() * 10));
        kafkaService.send(TopicNames.VISIT_EVENT, event);
    }

}
