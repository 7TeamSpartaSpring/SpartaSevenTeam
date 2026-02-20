package com.seventeamproject.api.order.repository;

import com.seventeamproject.api.order.dto.OrderResponse;
import com.seventeamproject.api.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Page<OrderResponse> search(Pageable pageable, Long id, String predicate);
}
