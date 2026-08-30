package dev.thilinifernando.account_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thilinifernando.account_service.model.InternalTransactionCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class InternalTransactionCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(InternalTransactionCreatedConsumer.class);
    private final ObjectMapper objectMapper;

    public InternalTransactionCreatedConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "new.internal.transaction.created", groupId = "account-service-group")
    public void listen(@Payload String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            InternalTransactionCreatedEvent event = objectMapper.readValue(payload, InternalTransactionCreatedEvent.class);
            log.info("Received internal transaction event from topic {}: {}", topic, event);
            // account-side processing: validate account, reserve funds, update balances, etc.
        } catch (Exception ex) {
            log.error("Failed to process Kafka message from topic {}: {}", topic, payload, ex);
        }
    }
}
