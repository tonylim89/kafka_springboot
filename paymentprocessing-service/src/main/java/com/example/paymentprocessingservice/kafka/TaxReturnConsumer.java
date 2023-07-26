package com.example.paymentprocessingservice.kafka;

import com.example.basedomains.dto.TaxReturnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaxReturnConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxReturnConsumer.class);

    private final TaxReturnProducer taxReturnProducer;

    public TaxReturnConsumer(TaxReturnProducer taxReturnProducer) {
        this.taxReturnProducer = taxReturnProducer;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "payment_group")
    public void consume(TaxReturnEvent taxReturnEvent) {
        LOGGER.info(String.format("Consumed event => %s", taxReturnEvent));

        // Check if the status is Pending Payment
        if ("Pending Payment".equals(taxReturnEvent.getStatus())) {
            // Simulate a payment process
            taxReturnEvent.setStatus("Completed");
            //Payment is made.
            taxReturnEvent.setMessage("Payment completed");

            // Send it back to the Kafka topic
            taxReturnProducer.sendTaxReturnEvent(taxReturnEvent);

            // Log the payment completion
            LOGGER.info(String.format("Payment completed for event => %s", taxReturnEvent));
        }
    }
}