package com.example.auditservice.kafka;

import com.example.basedomains.dto.TaxReturnEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TaxReturnProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxReturnProducer.class);

    private final NewTopic topic;
    private final KafkaTemplate<String, TaxReturnEvent> kafkaTemplate;

    public TaxReturnProducer(NewTopic topic, KafkaTemplate<String, TaxReturnEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaxReturnEvent(TaxReturnEvent taxReturnEvent){
        LOGGER.info(String.format("TaxReturn event => %s", taxReturnEvent.toString()));

        // create Message
        Message<TaxReturnEvent> message = MessageBuilder
                .withPayload(taxReturnEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }
}