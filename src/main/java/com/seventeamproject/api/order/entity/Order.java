package com.seventeamproject.api.order.entity;

import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.order.enums.OrderStatus;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.OrderException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE orders SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Order extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String orderNumber; // 주문번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;  // 주문상태
    @Column(nullable = false)
    private Long totalQuantity; // 주문 총 수량
    @Column(nullable = false)
    private Long totalAmount; // 주문 합계
    @Column(nullable = false)
    private LocalDateTime orderedAt; // 주문시간
    @Column(name = "registration_admin_id", nullable = false)
    private Long registrationManagerId; // cs주문시담당자
    @Column(length = 200)
    private String cancellationReason;  // 취소사유
    @Version
    private Long version;  // 낙관적락

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  // 고객 연관관계 1:N

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>(); // 상품(리스트) 연관관계 N:M

    // 이방법으로만 생성가능 like 생성자
    public static Order create(String orderNumber, Customer customer, Long registrationManagerId, List<OrderItem> items) {
       //validate
        if (orderNumber == null || orderNumber.isBlank()) {
            throw new OrderException(ErrorCode.ORDER_NUMBER_REQUIRED);
        }
        if (customer == null) {
            throw new OrderException(ErrorCode.ORDER_CUSTOMER_REQUIRED);
        }
        if (registrationManagerId == null) {
            throw new OrderException(ErrorCode.ORDER_MANAGER_REQUIRED);
        }
        if (items == null || items.isEmpty()) {
            throw new OrderException(ErrorCode.ORDER_ITEMS_REQUIRED);
        }

        Order order = new Order();
        order.orderNumber = orderNumber;
        order.customer = customer;
        order.status = OrderStatus.READY; // 주문하면 준비상태
        order.orderedAt = LocalDateTime.now();
        order.registrationManagerId = registrationManagerId; // sc주문이므로 담당자필수

        for (OrderItem item : items) {  // 주문상품 담는로직
            order.addItem(item);
        }
        order.recalculateTotalAmount(); // 총금액 계산로직
        return order;
    }

    // 주문상품 담는로직
    public void addItem(OrderItem item) {
        if (item == null) {
            throw new OrderException(ErrorCode.ORDER_ITEM_REQUIRED);
        }
        items.add(item);
        item.setOrder(this);
    }

    // 총금액 계산로직
    public void recalculateTotalAmount() {
        this.totalQuantity = items.stream()
                .mapToLong(OrderItem::getQuantity)
                .sum();
        this.totalAmount = items.stream()
                .mapToLong(OrderItem::getTotalAmount)
                .sum();
    }

    // 주문상태변경
    public void updateStatus(OrderStatus status) {
        if (status == null) {
            throw new OrderException(ErrorCode.ORDER_STATUS_REQUIRED);
        }
        if (this.status == OrderStatus.READY && status != OrderStatus.SHIPPING) {
            throw new OrderException(ErrorCode.ORDER_STATUS_BAD_REQUEST);
        }
        if (this.status == OrderStatus.SHIPPING && status != OrderStatus.COMPLETED) {
            throw new OrderException(ErrorCode.ORDER_STATUS_BAD_REQUEST);
        }
        if (this.status == OrderStatus.COMPLETED || status == OrderStatus.CANCELED) {
            throw new OrderException(ErrorCode.ORDER_UNAVAILABLE_STATUS);
        }
        this.status = status;
    }

    //주문취소
    public void cancel(String cancelReason) {
        if (this.status == OrderStatus.CANCELED) {
            throw new OrderException(ErrorCode.ORDER_ALREADY_CANCELED);
        }
        if (this.status != OrderStatus.READY) {
            throw new OrderException(ErrorCode.ORDER_CANCEL_NOT_ALLOWED);
        }
        if (cancelReason == null || cancelReason.isBlank()) {
            throw new OrderException(ErrorCode.ORDER_CANCEL_REASON_REQUIRED);
        }
        this.status = OrderStatus.CANCELED;
        this.cancellationReason = cancelReason;
    }
}

