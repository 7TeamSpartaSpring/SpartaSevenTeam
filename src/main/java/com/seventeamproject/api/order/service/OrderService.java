package com.seventeamproject.api.order.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.repository.AdminRepository;
import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.repository.CustomerRepository;
import com.seventeamproject.api.order.dto.*;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.entity.OrderItem;
import com.seventeamproject.api.order.entity.OrderStatus;
import com.seventeamproject.api.order.repository.OrderRepository;
import com.seventeamproject.api.product.entity.Product;
import com.seventeamproject.api.product.repository.ProductRepository;
import com.seventeamproject.api.product.service.ProductService;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    // 생성
    @Transactional
    public OrderResponse save(Authentication authentication, CreateOrderRequest request) {
        PrincipalUser admin = (PrincipalUser) authentication.getPrincipal();
        Long managerId = admin.getId();

        Customer customer = customerRepository.findById(request.customerId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 고객입니다."));

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest item : request.items()) {
            Long productId = item.productId();
            Long qty = item.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

            if (!productService.adjustStock(productId, qty)) {
                throw new IllegalStateException("재고 부족");
            }

            items.add(OrderItem.of(product, qty, product.getPrice()));
        }
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return new OrderResponse(orderRepository.save(Order.create(orderNumber, customer, managerId, items)));
    }




    //전체조회
    public PageResponse<OrderListResponse> getAll(Pageable pageable, String keyword, OrderStatus status) {
        return new PageResponse<>(
                orderRepository.search(pageable, keyword, status)
                        .map(order -> OrderListResponse.from(order, resolveManagerName(order)))
        );
    }

    //단건조회
    public GetOneOrderResponse getOne(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 주문"));

        Admin admin = null;
        if (order.getRegistrationManagerId() != null) {
            admin = adminRepository.findById(order.getRegistrationManagerId())
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 관리자"));
        }

        return new GetOneOrderResponse(order, admin);
    }

    //상태변경
    @Transactional
    public OrderResponse update(Long id, StatusUpdateRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지않는 주문ID입니다."));
                order.updateStatus(request.orderStatus());
        return new OrderResponse(order);
    }
    //주문취소
    @Transactional
    public OrderResponse cancel(Long id, OrderCancelRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지않는 주문ID입니다."));
        order.cancel(request.cancellationReason());
        return new OrderResponse(order);
    }

    private String resolveManagerName(Order order) {
        if (order.getRegistrationManagerId() == null) {
            return null;
        }
        return adminRepository.findById(order.getRegistrationManagerId())
                .map(Admin::getName)
                .orElse(null);
    }
}
