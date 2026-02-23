package com.seventeamproject.api.order.entity;

import com.seventeamproject.api.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long orderPrice; // 주문 당시 상품 가격 스냅샷

    @Column(nullable = false)
    private Long totalAmount; // orderPrice * quantity

    public static OrderItem of(Product product, Long quantity, Long orderPrice) {
        if (product == null) {
            throw new IllegalArgumentException("상품은 필수입니다.");
        }
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        if (orderPrice == null || orderPrice < 0) {
            throw new IllegalArgumentException("주문 가격은 0 이상이어야 합니다.");
        }

        OrderItem item = new OrderItem();
        item.product = product;
        item.quantity = quantity;
        item.orderPrice = orderPrice;
        item.totalAmount = orderPrice * quantity;
        return item;
    }

    void setOrder(Order order) {
        this.order = order;
    }
}
