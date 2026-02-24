package com.seventeamproject.api.review.entity;

import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "reviews",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE reviews SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Review extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private Long rating;
    @Column(nullable = false, length = 100)
    private String content;

    public Review(Product product, Order order,  Customer customer, Long rating, String content) {
        this.product = product;
        this.order = order;
        this.customer = customer;
        this.rating = rating;
        this.content = content;
    }

    public Review update(String content) {
        this.content = content;
        return this;
    }
}


