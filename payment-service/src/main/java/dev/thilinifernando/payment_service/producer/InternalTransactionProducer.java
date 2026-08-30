package dev.thilinifernando.payment_service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thilinifernando.payment_service.model.InternalTransactionCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InternalTransactionProducer {

    private static final Logger log = LoggerFactory.getLogger(InternalTransactionProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public InternalTransactionProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishInternalTransactionCreated(InternalTransactionCreatedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("new.internal.transaction.created", event.transactionId(), payload);
            log.info("Published internal transaction event to Kafka topic new.internal.transaction.created: {}", event);
        } catch (Exception ex) {
            log.error("Failed to publish internal transaction event: {}", event, ex);
            throw new RuntimeException("Unable to publish internal transaction event", ex);
        }
    }
}
