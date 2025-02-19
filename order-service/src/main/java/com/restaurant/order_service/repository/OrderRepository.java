package com.restaurant.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.order_service.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{   
}
