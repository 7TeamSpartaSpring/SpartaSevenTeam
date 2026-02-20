package com.seventeamproject.api.review.entity;

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
@SQLDelete(sql = "UPDATE schedules SET deleted_at = now() WHERE id = ?")
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
    private Long orderId;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "customer_id", nullable = false)
    private Long customer;

    private Long rating;
    private String content;

    public Review(Long product, Long order, Long customer, Long rating, String content) {
        this.product = product;
        this.orderId = order;
        this.customer = customer;
        this.rating = rating;
        this.content = content;
    }
}


