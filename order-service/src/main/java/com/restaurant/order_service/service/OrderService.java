package com.restaurant.order_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restaurant.order_service.dto.ClientResponse;
import com.restaurant.order_service.feignClient.ClientServiceFeignClient;
import com.restaurant.order_service.model.Order;
import com.restaurant.order_service.model.OrderStatus;
import com.restaurant.order_service.repository.OrderRepository;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ClientServiceFeignClient clientServiceFeignClient;

    
    public OrderService(OrderRepository orderRepository, ClientServiceFeignClient clientServiceFeignClient) {
        this.orderRepository = orderRepository;
        this.clientServiceFeignClient = clientServiceFeignClient;
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
}
