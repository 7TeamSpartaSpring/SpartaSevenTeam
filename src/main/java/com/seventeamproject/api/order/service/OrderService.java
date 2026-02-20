package com.seventeamproject.api.order.service;

import com.seventeamproject.api.order.dto.OrderRequest;
import com.seventeamproject.api.order.dto.OrderResponse;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.repository.OrderRepository;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.example.one.service.OneReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OneReader oneReader;

    @Transactional
    public OrderResponse save(Long id, OrderRequest request) {
        return new OrderResponse(orderRepository.save(new Order(
                request.content(),
                request.value(),
                oneReader.getEntty(id)
        )));
    }

    public PageResponse<OrderResponse> getAll(Pageable pageable, Long id, String content) {
        return new PageResponse<>(orderRepository.search(pageable, id, content));
    }

    public OrderResponse getOne(Long id) {
        return new OrderResponse(orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")));
    }

    @Transactional
    public OrderResponse update(Long id, OrderRequest request) {
        return new OrderResponse(orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).updateStatus(request.content(), request.value()));

    }

    @Transactional
    public void delete(Long id, Long userId) {
        orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).delete(userId);
    }
}