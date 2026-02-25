package com.seventeamproject.api.product.product.entity;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.entity.Category;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.product.sku.enums.SkuStatusEnum;
import com.seventeamproject.common.entity.SoftDeletableEntity;
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
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    public Product(String name, Category category, Long price, ProductStatus status, Admin admin) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.status = status;
        this.admin = admin;
    }

    public Product update(String name, Category category, Long price) {
        this.name = name;
        this.category = category;
        this.price = price;
        return this;
    }

    public Product setStatus(ProductStatus status) {
        this.status = status;
        return this;
    }
}
