package com.seventeamproject.admin.entity;

import com.seventeamproject.admin.enums.AdminRoleEnum;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import com.seventeamproject.example.many.entity.Many;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private AdminRoleEnum role;
    private boolean isEnabled;

    public Admin(String email, String password, String name, AdminRoleEnum role, boolean isEnabled) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.isEnabled = isEnabled;
    }
}