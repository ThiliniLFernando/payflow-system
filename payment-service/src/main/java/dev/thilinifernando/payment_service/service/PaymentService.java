package dev.thilinifernando.payment_service.service;

import dev.thilinifernando.payment_service.model.InternalTransactionCreatedEvent;
import dev.thilinifernando.payment_service.model.PaymentRequest;
import dev.thilinifernando.payment_service.producer.InternalTransactionProducer;
import dev.thilinifernando.payment_service.util.ExternalService;
import dev.thilinifernando.payment_service.util.response.AccountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final ExternalService externalService;
    private final InternalTransactionProducer internalTransactionProducer;

    @Autowired
    public PaymentService(ExternalService externalService, InternalTransactionProducer internalTransactionProducer) {
        this.externalService = externalService;
        this.internalTransactionProducer = internalTransactionProducer;
    }

    public void proceedPaymentRequest(PaymentRequest paymentRequest) {
        AccountResponse accountResponse = externalService.getAccountDetails(paymentRequest.userId());
        if (accountResponse.balance().compareTo(paymentRequest.amount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        InternalTransactionCreatedEvent event = new InternalTransactionCreatedEvent(
                UUID.randomUUID().toString(),
                paymentRequest.userId(),
                accountResponse.accountNumber(),
                paymentRequest.amount(),
                paymentRequest.currency(),
                "CREATED",
                "payment-service",
                LocalDateTime.now()
        );

        internalTransactionProducer.publishInternalTransactionCreated(event);
        log.info("Payment approved and internal transaction event published: {}", event);
    }

    public void processInternalTransactionCreatedEvent(InternalTransactionCreatedEvent event) {
        log.info("Processing internal transaction event {} for account {}", event.transactionId(), event.accountNumber());

        if (event.amount() == null || event.userId() == null || event.accountNumber() == null) {
            log.warn("Skipping invalid internal transaction event: {}", event);
            return;
        }

        // Add any validation here: check if account exists, validate amount, perform internal account deduction, etc.
    }
}
