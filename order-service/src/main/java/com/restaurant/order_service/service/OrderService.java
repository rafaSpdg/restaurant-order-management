package com.restaurant.order_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.order_service.dto.ClientResponse;
import com.restaurant.order_service.feignClient.ClientServiceFeignClient;
import com.restaurant.order_service.model.Order;
import com.restaurant.order_service.model.OrderStatus;
import com.restaurant.order_service.model.OrderUpdate;
import com.restaurant.order_service.repository.OrderRepository;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ClientServiceFeignClient clientServiceFeignClient;
    private final RabbitTemplate rabbitTemplate;

    
    public OrderService(OrderRepository orderRepository, ClientServiceFeignClient clientServiceFeignClient, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.clientServiceFeignClient = clientServiceFeignClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {

        //Verifica se o cliente existe chamando o Serviço de Clientes
        ClientResponse client = clientServiceFeignClient.getClientById(order.getClientId());

        if (client == null) {
            throw new RuntimeException("Client not found.");
        }

        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public Order updateOrderStatus(int id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found."));
        
        order.setStatus(status);

        return orderRepository.save(order);
    }

    public void notifyOrderReady(Long orderId) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(new OrderUpdate(orderId, OrderStatus.READY));

            rabbitTemplate.convertAndSend("orderExchange", "order.ready", message);
            System.out.println("Message send to RabbitMQ: Pedido " +  orderId + " está pronto.");
        } catch (JsonProcessingException e) {
            System.err.println("Error converting message to JSON: " + e.getMessage());
        }

        
    }
}
