package com.seventeamproject.api.product.category.entity;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.enums.CategoryStatusEnum;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatusEnum;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "categorys",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE categorys SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Category extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    private String code;
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatusEnum status;

    public Category(String code, String name, CategoryStatusEnum status, Admin admin) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.admin = admin;
    }

    public Category update(String code, String name, CategoryStatusEnum status) {
        this.code = code;
        this.name = name;
        this.status = status;
        return this;
    }
}
