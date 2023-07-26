package com.example.paymentprocessingservice.kafka;
import com.example.basedomains.dto.TaxReturnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class TaxReturnProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaxReturnProducer.class);

    private final KafkaTemplate<String, TaxReturnEvent> kafkaTemplate;

    public TaxReturnProducer(KafkaTemplate<String, TaxReturnEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.producer.topic-name}")
    private String topicName;

    public void sendTaxReturnEvent(TaxReturnEvent taxReturnEvent) {
        LOGGER.info(String.format("Producing event => %s", taxReturnEvent));
        this.kafkaTemplate.send(topicName, taxReturnEvent);
    }
}