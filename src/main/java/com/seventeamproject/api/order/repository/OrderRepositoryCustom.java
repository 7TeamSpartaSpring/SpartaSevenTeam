package com.seventeamproject.api.order.repository;

import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<Order> search(Pageable pageable, String keyword, OrderStatus status);
}


