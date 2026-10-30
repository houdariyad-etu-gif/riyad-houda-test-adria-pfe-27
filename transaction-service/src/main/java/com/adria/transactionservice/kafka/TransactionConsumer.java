package com.adria.transactionservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    @KafkaListener(topics = "bank.transactions.created", groupId = "transaction-group")
    public void consume(String event) {
        log.info("Événement Kafka reçu dans bank.transactions.created : {}", event);
    }
}