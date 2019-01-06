package com.example.wyl.rabbitMq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQQueue {

    @Bean
    public Queue userQueue() {
        return new Queue("wang");
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("directExchange", true, false);
    }

    @Bean
    public Binding bindingExchangeMsg(Queue userQueue, DirectExchange exchange) {

        return BindingBuilder.bind(userQueue).to(exchange).with("testHello");
    }
}
