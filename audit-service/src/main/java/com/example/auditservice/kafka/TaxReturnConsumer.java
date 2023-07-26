package com.example.auditservice.kafka;

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

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "audit_group")
    public void consume(TaxReturnEvent taxReturnEvent) {
        LOGGER.info(String.format("Consumed event => %s", taxReturnEvent));

        // Check if the event has been processed
        if (!taxReturnEvent.isProcessed()) {
            // Check if the income is greater than 1000
            if (taxReturnEvent.getTaxReturn().getIncome() > 1000) {
                // Flag the tax return for audit
                taxReturnEvent.setStatus("Pending Audit");
                taxReturnEvent.setMessage("Tax return flagged for audit");
            } else {
                taxReturnEvent.setStatus("Pending Payment");
                taxReturnEvent.setMessage("Tax return approved, pending payment");
            }

            // Mark the event as processed
            taxReturnEvent.setProcessed(true);

            // Send it back to the Kafka topic
            taxReturnProducer.sendTaxReturnEvent(taxReturnEvent);
        }
    }
}