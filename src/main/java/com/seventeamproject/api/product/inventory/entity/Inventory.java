package com.seventeamproject.api.product.inventory.entity;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.entity.Category;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.product.sku.entity.Sku;
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
@Table(name = "inventorys",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE inventorys SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Inventory extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;

    private Long qty;
    private Long reserved_qty;


    public Inventory(Product product, Sku sku, Admin admin, Long qty) {
        this.product = product;
        this.sku = sku;
        this.admin = admin;
        this.qty = qty;
        this.reserved_qty = 0L;
    }

    public Long setQty(Long qty) {
        this.qty = qty;
        if(this.qty > 0){
            if(product.getStatus() == ProductStatus.SOLD_OUT){
                product.setStatus(ProductStatus.AVAILABLE);
            }

            if(sku.getStatus() == SkuStatusEnum.SOLD_OUT){
                sku.setStatus(SkuStatusEnum.AVAILABLE);
            }

        }else {
            if(product.getStatus() != ProductStatus.DISCONTINUED){
                product.setStatus(ProductStatus.SOLD_OUT);
            }
            if(sku.getStatus() != SkuStatusEnum.DISCONTINUED){
                sku.setStatus(SkuStatusEnum.SOLD_OUT);
            }
        }
        return this.qty;
    }
}
