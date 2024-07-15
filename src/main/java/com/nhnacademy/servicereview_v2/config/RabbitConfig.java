package com.nhnacademy.servicereview_v2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    @Value("${rabbit.review.exchange.name}")
    private String reviewExchangeName;
    @Value("${rabbit.review.queue.name}")
    private String reviewQueueName;
    @Value("${rabbit.review.routing.key}")
    private String reviewRoutingKey;
    @Value("${rabbit.review.dlq.routing.key}")
    private String reviewDlqRoutingKey;

    @Bean
    DirectExchange reviewExchange() {
        return new DirectExchange(reviewExchangeName);
    }

    @Bean
    Queue reviewQueue() {
        return QueueBuilder.durable("review-queue")
                .withArgument("x-dead-letter-exchange", reviewExchangeName)
                .withArgument("x-dead-letter-routing-key", reviewRoutingKey)
                .build();
    }

    @Bean
    Binding reviewBinding(Queue loginQueue, DirectExchange loginExchange) {
        return BindingBuilder.bind(loginQueue).to(loginExchange).with(reviewRoutingKey);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
