package com.seventeamproject.api.order.repository;

import com.seventeamproject.api.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<OrderResponse> search(Pageable pageable, Long id, String predicate);
}