package com.seventeamproject.api.review.entity;

import com.seventeamproject.api.product.entity.Product;
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

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "product_id", nullable = false)
    private Long product;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "order_id", nullable = false)
    @Column(name = "order_id")
    private Long order;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "customer_id", nullable = false)
    private Long customer;

    private Long reviewQty;
    @Column(nullable = false)
    private Long rating;
    @Column(nullable = false, length = 100)
    private String content;

    public Review(Long product, Long order,  Long customer, Long reviewQty, Long rating, String content) {
        this.product = product;
        this.order = order;
        this.customer = customer;
        this.reviewQty = reviewQty;
        this.rating = rating;
        this.content = content;
    }

    public Review update(String content) {
        this.content = content;
        return this;
    }
}


