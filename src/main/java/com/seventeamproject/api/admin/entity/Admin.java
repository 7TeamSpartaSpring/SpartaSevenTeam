package com.seventeamproject.api.admin.entity;

import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;
import com.seventeamproject.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminRoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatusEnum status;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    @Column(length = 500)
    private String rejectedReason;


    // 관리자 가입 신청 시 사용하는 생성자
    public Admin(String email, String password, String name, String phone, AdminRoleEnum role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.status = AdminStatusEnum.PENDING;
    }

    // 로그인 가능 여부
    public boolean isEnabled() {
        return this.status == AdminStatusEnum.ACTIVE && !isDeleted();
    }

    // 승인 처리
    public void approve() {
        if (this.status != AdminStatusEnum.PENDING) {
            throw new IllegalStateException("승인대기 상태만 승인할 수 있습니다.");
        }
        this.status = AdminStatusEnum.ACTIVE;
        this.approvedAt = LocalDateTime.now();
        this.rejectedAt = null;
        this.rejectedReason = null;
    }

    // 거부 처리
    public void reject(String reason) {
        if (this.status != AdminStatusEnum.PENDING) {
            throw new IllegalStateException("승인대기 상태만 거부할 수 있습니다.");
        }
        this.status = AdminStatusEnum.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.rejectedReason = reason;
    }

    //  역할 변경
    public void changeRole(AdminRoleEnum role) {
        this.role = role;
    }

    // 상태 변경
    public void changeStatus(AdminStatusEnum status) {
        this.status = status;
    }

    // 내 프로필 수정용
    public void updateProfile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // 비밀번호 변경
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    // 시드용
    public static Admin seed(String email, String encodedPassword, String name, String phone, AdminRoleEnum role) {
        Admin admin = new Admin(email, encodedPassword, name, phone, role);
        admin.status = AdminStatusEnum.ACTIVE;
        admin.approvedAt = LocalDateTime.now();
        return admin;
    }
}