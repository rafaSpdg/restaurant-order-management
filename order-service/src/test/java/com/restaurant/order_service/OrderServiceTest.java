package com.restaurant.order_service;

import java.time.LocalDateTime;
import java.util.Arrays;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.order_service.dto.ClientResponse;
import com.restaurant.order_service.feignClient.ClientServiceFeignClient;
import com.restaurant.order_service.model.Order;
import com.restaurant.order_service.model.OrderStatus;
import com.restaurant.order_service.repository.OrderRepository;
import com.restaurant.order_service.service.OrderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ClientServiceFeignClient clientServiceFeignClient;


    @Test
    void testCreatOrderWithFeignClient() {

        //simulamos o retorno do Feign Client
        when(clientServiceFeignClient.getClientById(1)).thenReturn(new ClientResponse(1, "Rafa"));


        Order order = new Order (1,"Rafa", "Table 5", "49.99", OrderStatus.PENDING, LocalDateTime.now());

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order(1,"Rafa", "Mesa 3", "30.0", OrderStatus.PENDING, LocalDateTime.now());
        Order order2 = new Order(5,"Rafa", "Mesa 7","25.0", OrderStatus.READY, LocalDateTime.now());

        //mocking(simulação) -> simulação do repositório orderRepository
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        assertEquals(2, orderService.getAllOrders().size());
    }

}
