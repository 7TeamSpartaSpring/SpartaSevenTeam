package com.seventeamproject.api.order.entity;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.common.entity.BaseEntity;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.OrderException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // 주문 연관관계

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // 상품 연관관계

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;  // sku 연관관계

    @Column(nullable = false)
    private Long quantity;  // 주문수량

    @Column(nullable = false)
    private Long orderPrice; // 주문 당시 상품 가격 스냅샷

    @Column(nullable = false)
    private Long totalAmount; // orderPrice * quantity


    public static OrderItem of(Product product, Sku sku, Long quantity, Long orderPrice) {
        if (product == null) {
            throw new OrderException(ErrorCode.ORDER_ITEM_PRODUCT_REQUIRED);
        }
        if (sku == null) {
            throw new OrderException(ErrorCode.ORDER_ITEM_SKU_REQUIRED);
        }
        if (quantity == null || quantity < 1) {
            throw new OrderException(ErrorCode.ORDER_ITEM_QUANTITY_INVALID);
        }
        if (orderPrice == null || orderPrice < 0) {
            throw new OrderException(ErrorCode.ORDER_ITEM_PRICE_INVALID);
        }

        OrderItem item = new OrderItem();
        item.product = product;
        item.sku = sku;
        item.quantity = quantity;
        item.orderPrice = orderPrice;
        item.totalAmount = orderPrice * quantity;
        return item;
    }

    void setOrder(Order order) {
        this.order = order;
    }
}
