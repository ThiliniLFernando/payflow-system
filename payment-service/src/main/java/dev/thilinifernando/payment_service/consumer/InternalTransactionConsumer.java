package dev.thilinifernando.payment_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thilinifernando.payment_service.model.InternalTransactionCreatedEvent;
import dev.thilinifernando.payment_service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class InternalTransactionConsumer {

    private static final Logger log = LoggerFactory.getLogger(InternalTransactionConsumer.class);

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    public InternalTransactionConsumer(PaymentService paymentService, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = {
                    "${app.kafka.topics.internal-transaction-created:new.internal.transaction.created}",
                    "${app.kafka.topics.internal-transaction-created-alt:new.internl.trnsction.cretead}"
            },
            groupId = "${spring.kafka.consumer.group-id:payment-service-group}"
    )
    public void listen(
            @Payload String payload,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        try {
            InternalTransactionCreatedEvent event = objectMapper.readValue(payload, InternalTransactionCreatedEvent.class);
            log.info("Received internal transaction event from topic {}: {}", topic, event);
            paymentService.processInternalTransactionCreatedEvent(event);
        } catch (Exception ex) {
            log.error("Failed to process Kafka message from topic {}: {}", topic, payload, ex);
        }
    }
}
