package com.example.demo.message;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${kafka.image.upload.event.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String message) {
        logger.info("received payload='{}'", message);
        //payload = consumerRecord.toString();

        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

}
