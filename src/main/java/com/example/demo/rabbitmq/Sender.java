package com.example.demo.rabbitmq;

import com.example.demo.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Sender {

    private final RabbitMessagingTemplate template;

    private final String queueName = "helloQ";
    private final String exchangeName = "helloDirect";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("qooqoo");
    }

    public void send(Order order) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJsonString = objectMapper.writeValueAsString(order);
        template.convertAndSend(exchangeName, "qooqoo", orderJsonString);
    }

}
