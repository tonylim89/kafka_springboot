# kafka_demo

This demo is done using Kafka, Springboot, and React.js and is done to demonstrate event-driven microservice architecture using Kafka.

## Setup
1. First, setup Kafka, run zookeeper first, then run kafka setup on CLI.
2. Next run the services in Springboot.
3. Next run the React.js file.

Enter id and income to post to the backend server.

## The Services
### taxreturnsubmission-service
This service takes in the id and income, sets status as submitted, and passes it as an event to the Kafka broker as an event.

### audit-service
This service listens for events from the Kafka broker, specifically for the status submitted. It then processes it into pending payment or flagged for audit depending on if the income is above 1000. It then sends the event to Kafka listener.

### paymentprocessing-service
This service listens for events from the Kafka broker, specifically for the pending payment status. It would then change the status to completed to simulate payment being made and send the event to the Kafka broker.

### notification-service
This service listens for a few items:
```java
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
```
Once it listens and ascertains the status, it would perform the corresponding logs to simulate an email is sent.

Currently, the demo does not link the microservices to persistent storage, thus the approval of audit cannot be implemented. This would not be improved upon in the future as it is only to demonstrate how Kafka broker works in a tax collecting agency.
