package com.restaurant.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("orderExchange");
    }

    @Bean
    public Binding binding(Queue orderQueue, TopicExchange exchange ) {
        return BindingBuilder.bind(orderQueue).to(exchange).with("order.ready");
    }
}
