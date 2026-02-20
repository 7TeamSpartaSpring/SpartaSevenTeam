package com.seventeamproject.api.product.entity;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import com.seventeamproject.example.many.entity.Many;
import com.seventeamproject.example.one.entity.One;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "products",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE products SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Product extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;
    private Long totalQty;
    private String status;
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    private Long value;

    public Product(String name, Long categoryId, Long price, Long totalQty, String status) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.totalQty = totalQty;
        this.status = status;
    }

    public Product update(String name, Long categoryId, Long price) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        return this;
    }
}
