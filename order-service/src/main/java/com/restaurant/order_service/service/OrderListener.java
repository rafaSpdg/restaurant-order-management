package com.restaurant.order_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.order_service.model.OrderStatus;
import com.restaurant.order_service.model.OrderUpdate;

@Service
public class OrderListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @RabbitListener (queues = "orderQueue") // nome da fila onde a mensagem será recebida
    public void receiveOrderReadyMessage(String message) {
        
        try {
            OrderUpdate orderUpdate = objectMapper.readValue(message, OrderUpdate.class);
            if (orderUpdate.getStatus() == OrderStatus.READY) {
                System.out.println("Order Ready! ID: " + orderUpdate.getOrderId());
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
