package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.rabbitmq.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final Sender sender;

    @PostMapping("/messages")
    public String sendMessage(@RequestBody Order order) {
        try {
            sender.send(order);
            return "send message success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "send message fail";
        }
    }

}
