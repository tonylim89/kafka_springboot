package com.example.notificationservice.kafka;

import com.example.basedomains.dto.TaxReturnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaxReturnConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxReturnConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "notification_group")
    public void consume(TaxReturnEvent taxReturnEvent) {
        LOGGER.info(String.format("Consumed event => %s", taxReturnEvent));
        //Send out notification to show that it works. Won't be implementing email service.
        switch (taxReturnEvent.getStatus()) {
            case "Submitted":
                LOGGER.info("Sending confirmation of submission email to user: " + taxReturnEvent.getTaxReturn().getTaxpayerId());
                break;
            case "Pending Audit":
                LOGGER.info("Sending Audit Processing email to Tax Officer for taxpayer: " + taxReturnEvent.getTaxReturn().getTaxpayerId());
                break;
            case "Completed":
                LOGGER.info("Sending thank you for payment email to user: " + taxReturnEvent.getTaxReturn().getTaxpayerId());
                break;
        }
    }
}