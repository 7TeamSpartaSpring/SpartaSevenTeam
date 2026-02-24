package com.seventeamproject.api.order.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.repository.AdminRepository;
import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.repository.CustomerRepository;
import com.seventeamproject.api.order.dto.*;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.entity.OrderItem;
import com.seventeamproject.api.order.enums.OrderStatus;
import com.seventeamproject.api.order.repository.OrderRepository;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.repository.ProductRepository;
import com.seventeamproject.api.product.product.service.ProductService;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.repository.SkuRepository;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.*;
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
    private final SkuRepository skuRepository;
    private final AdminRepository adminRepository;

    // 생성
    @Transactional
    public OrderResponse save(Authentication authentication, CreateOrderRequest request) {
        // 담당자정보가져오기
        PrincipalUser admin = (PrincipalUser) authentication.getPrincipal();
        Long managerId = admin.getId();

        // 고객정보가져오기
        Customer customer = customerRepository.findById(request.customerId()).orElseThrow(
                () -> new CustomerException(ErrorCode.CUSTOMER_NOT_FOUND));

        List<OrderItem> items = new ArrayList<>();

        // 상품확인및 재고확인
        for (OrderItemRequest item : request.items()) {
            Long skuId = item.skuId();
            Long qty = item.quantity();

            Sku sku = skuRepository.findById(skuId)
                    .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

            if(!productService.canOrder(sku.getId())){
                throw new ProductException(ErrorCode.ORDER_UNAVAILABLE_STATUS);
            }
            if (productService.adjustStock(sku.getId(), -qty) < 0) {
                throw new ProductException(ErrorCode.ORDER_OUT_OF_STOCK);
            }

            items.add(OrderItem.of(sku.getProduct(), sku, qty, sku.getPrice()));
        }

        //주문번호 랜덤값지정
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
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        Admin admin = null;
        if (order.getRegistrationManagerId() != null) {
            admin = adminRepository.findById(order.getRegistrationManagerId())
                    .orElseThrow(() -> new MemberException(ErrorCode.ADMIN_NOT_FOUND));
        }

        return new GetOneOrderResponse(order, admin);
    }

    //상태변경
    @Transactional
    public OrderResponse update(Long id, StatusUpdateRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
                order.updateStatus(request.orderStatus());
        return new OrderResponse(order);
    }
    //주문취소
    @Transactional
    public OrderResponse cancel(Long id, OrderCancelRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        // 상품확인및 재고확인
        for (OrderItem item : order.getItems()) {
            Sku sku = item.getSku();
            Long qty = item.getQuantity();

            if (productService.adjustStock(sku.getId(), qty) < 0) {
                throw new ProductException(ErrorCode.ORDER_CANCEL_FAIL);
            }
        }

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


