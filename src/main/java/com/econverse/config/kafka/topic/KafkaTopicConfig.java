package com.econverse.config.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // ORDER FLOW
    @Bean
    public NewTopic orderCreated() {
        return TopicBuilder.name("order.created")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderCancelled() {
        return TopicBuilder.name("order.cancelled")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderMatched() {
        return TopicBuilder.name("order.matched")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // TRADE EXECUTION
    @Bean
    public NewTopic tradeExecuted() {
        return TopicBuilder.name("trade.executed")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // SHARE PRICE
    @Bean
    public NewTopic sharePriceUpdated() {
        return TopicBuilder.name("share.price.updated")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // WALLET
    @Bean
    public NewTopic walletBalanceChanged() {
        return TopicBuilder.name("wallet.balance.changed")
                .partitions(3)
                .replicas(1)
                .build();
    }
}