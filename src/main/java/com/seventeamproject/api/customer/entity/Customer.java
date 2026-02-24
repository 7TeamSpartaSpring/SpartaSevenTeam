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
    private Long id; //고객 고유번호

    private String name; //고객 이름

    @Column(unique = true, nullable = false)
    private String email; //고객 이메일

    @Column(unique = true, nullable = false)
    private String phone; // 고객 전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status; // 고객 상태


    public Customer(Long id, String name, String email,String phone,CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    // 고객 정보 업데이트
    public Customer update(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
        return this;
    }

    // 고객 상태 업데이트
    public Customer updateStatus(CustomerStatus status){
        this.status = status;
        return this;
    }
}
