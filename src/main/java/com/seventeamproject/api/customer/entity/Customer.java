package com.seventeamproject.api.customer.entity;

import com.seventeamproject.api.customer.enums.CustomerStatus;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "customers",
        indexes = {
            @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE customers SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Customer extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;


    public Customer(Long id, String name, String email,String phone,CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public Customer update(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
        return this;
    }

    public Customer updateStatus(CustomerStatus status){
        this.status = status;
        return this;
    }
}
