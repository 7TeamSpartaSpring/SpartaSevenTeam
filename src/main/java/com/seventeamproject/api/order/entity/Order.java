package com.seventeamproject.api.order.entity;

import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private Long totalAmount; // 주문 합계
    @Column(nullable = false)
    private LocalDateTime orderedAt;
    @Column(length = 200)
    private String registrationManager;
    @Column(length = 200)
    private String cancellationReason;
    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public static Order create(String orderNumber, Customer customer, String registrationManager, List<OrderItem> items) {
        if (orderNumber == null || orderNumber.isBlank()) {
            throw new IllegalArgumentException("주문번호는 필수입니다.");
        }
        if (customer == null) {
            throw new IllegalArgumentException("고객은 필수입니다.");
        }
        if (registrationManager == null || registrationManager.isBlank()) {
            throw new IllegalArgumentException("담당자명은 필수입니다.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 최소 1개 필요합니다.");
        }

        Order order = new Order();
        order.orderNumber = orderNumber;
        order.customer = customer;
        order.status = OrderStatus.READY;
        order.orderedAt = LocalDateTime.now();
        order.registrationManager = registrationManager;

        for (OrderItem item : items) {
            order.addItem(item);
        }
        order.recalculateTotalAmount();
        return order;
    }

    public void addItem(OrderItem item) {
        Objects.requireNonNull(item, "주문 항목은 필수입니다.");
        items.add(item);
        item.setOrder(this);
    }

    public void recalculateTotalAmount() {
        this.totalAmount = items.stream()
                .mapToLong(OrderItem::getTotalAmount)
                .sum();
    }

    public void updateStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status, "주문 상태는 필수입니다.");
    }

    public void cancel(String cancelReason) {
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 요청");
        }
        if (this.status != OrderStatus.READY) {
            throw new IllegalStateException("준비중 상태에서만 취소 가능합니다.");
        }
        if (cancelReason == null || cancelReason.isBlank()) {
            throw new IllegalArgumentException("취소 사유는 필수입니다.");
        }
        this.status = OrderStatus.CANCELED;
        this.cancellationReason = cancelReason;
    }
}
