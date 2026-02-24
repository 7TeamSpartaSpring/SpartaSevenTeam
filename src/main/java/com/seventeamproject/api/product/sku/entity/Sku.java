package com.seventeamproject.api.product.sku.entity;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.enums.CategoryStatusEnum;
import com.seventeamproject.api.product.product.entity.Product;
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
@Table(name = "skus",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE skus SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Sku extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    private Long price;
    @Enumerated(EnumType.STRING)
    private SkuStatusEnum status;

    public Sku(Product product, Admin admin, Long price, SkuStatusEnum status) {
        this.product = product;
        this.admin = admin;
        this.price = price;
        this.status = status;
    }

    public SkuStatusEnum setStatus(SkuStatusEnum status) {
        return this.status = status;
    }
}
