package com.seventeamproject.api.order.repository;

import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Page<Order> search(Pageable pageable, String keyword, OrderStatus status);
}
