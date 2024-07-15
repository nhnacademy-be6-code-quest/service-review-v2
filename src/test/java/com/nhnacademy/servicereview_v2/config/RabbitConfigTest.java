package com.nhnacademy.servicereview_v2.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "rabbit.review.exchange.name=test-review-exchange",
        "rabbit.review.queue.name=test-review-queue",
        "rabbit.review.routing.key=test-review-routing-key",
        "rabbit.review.dlq.routing.key=test-review-dlq-routing-key"
})
class RabbitConfigTest {

    @Autowired
    private RabbitConfig rabbitConfig;

    @MockBean
    private ConnectionFactory connectionFactory;

    @Test
    void testReviewExchange() {
        DirectExchange exchange = rabbitConfig.reviewExchange();
        assertNotNull(exchange);
        assertEquals("test-review-exchange", exchange.getName());
    }

    @Test
    void testReviewQueue() {
        Queue queue = rabbitConfig.reviewQueue();
        assertNotNull(queue);
        assertEquals("review-queue", queue.getName());

        // Dead Letter Exchange 설정 확인
        Object deadLetterExchange = queue.getArguments().get("x-dead-letter-exchange");
        assertEquals("test-review-exchange", deadLetterExchange);

        // Dead Letter Routing Key 설정 확인
        Object deadLetterRoutingKey = queue.getArguments().get("x-dead-letter-routing-key");
        assertEquals("test-review-routing-key", deadLetterRoutingKey);
    }

    @Test
    void testReviewBinding() {
        Queue queue = rabbitConfig.reviewQueue();
        DirectExchange exchange = rabbitConfig.reviewExchange();
        Binding binding = rabbitConfig.reviewBinding(queue, exchange);

        assertNotNull(binding);
        assertEquals("test-review-routing-key", binding.getRoutingKey());
        assertEquals(queue.getName(), binding.getDestination());
        assertEquals(exchange.getName(), binding.getExchange());
    }

    @Test
    void testRabbitTemplate() {
        RabbitTemplate rabbitTemplate = rabbitConfig.rabbitTemplate(connectionFactory);
        assertNotNull(rabbitTemplate);
        assertTrue(rabbitTemplate.getMessageConverter() instanceof org.springframework.amqp.support.converter.Jackson2JsonMessageConverter);
    }
}