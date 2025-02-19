package com.restaurant.order_service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.order_service.dto.ClientResponse;
import com.restaurant.order_service.feignClient.ClientServiceFeignClient;
import com.restaurant.order_service.model.Order;
import com.restaurant.order_service.model.OrderStatus;
import com.restaurant.order_service.repository.OrderRepository;
import com.restaurant.order_service.service.OrderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    
    private ClientServiceFeignClient clientServiceFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientServiceFeignClient = Mockito.mock(ClientServiceFeignClient.class);
    }

    @Test
    void testCreatOrder() {

        //simulamos o retorno do Feign Client
        when(clientServiceFeignClient.getClientById("0")).thenReturn(new ClientResponse("0", "João"));


        Order order = new Order ("0","João", "Table 5", "49.99", OrderStatus.PENDING, LocalDateTime.now());

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order("3","Maria", "Mesa 3", "30.0", OrderStatus.PENDING, LocalDateTime.now());
        Order order2 = new Order("4","Carlos", "Mesa 7","25.0", OrderStatus.READY, LocalDateTime.now());

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        assertEquals(2, orderService.getAllOrders().size());
    }

    @Test
    void testGetOrderById() {
        Order order = new Order("1","Ana", "Mesa 2", "40.0", OrderStatus.COMPLETED, LocalDateTime.now());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> foundOrder = orderService.getOrderById(1L);

        assertTrue(foundOrder.isPresent());
        assertEquals("Ana", foundOrder.get().getClientName());
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order("2","Ana", "Mesa 1", "30.00", OrderStatus.PENDING, LocalDateTime.now());
        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertNotNull(createdOrder.getId());
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
    }
}
