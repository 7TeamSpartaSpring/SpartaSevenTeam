package com.seventeamproject.api.admin.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.admin.dto.AdminResponse;
import com.seventeamproject.api.admin.dto.QAdminResponse;
import com.seventeamproject.api.admin.entity.QAdmin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 관리자 목록 검색/필터/정렬/페이징 조회
    @Override
    public Page<AdminResponse> search(Pageable pageable, String keyword, AdminRoleEnum role, AdminStatusEnum status) {
        QAdmin admin = QAdmin.admin;

        // 목록 조회
        List<AdminResponse> content = queryFactory
                .select(new QAdminResponse(
                        admin.id,
                        admin.email,
                        admin.name,
                        admin.phone,
                        admin.role,
                        admin.status,
                        admin.createdAt,
                        admin.modifiedAt,
                        admin.approvedAt,
                        admin.rejectedAt,
                        admin.rejectedReason
                ))
                .from(admin)
                .where(
                        keywordContains(keyword, admin),
                        roleEq(role, admin),
                        statusEq(status, admin)
                )
                .orderBy(toOrderSpecifiers(pageable.getSort(), admin))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        Long total = queryFactory
                .select(admin.count())
                .from(admin)
                .where(
                        keywordContains(keyword, admin),
                        roleEq(role, admin),
                        statusEq(status, admin)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0L : total);
    }

    // keyword가 있으면 이름/이메일 contains 검색 조건 생성
    private BooleanExpression keywordContains(String keyword, QAdmin admin) {
        if (keyword == null || keyword.isBlank()) return null;
        return admin.name.containsIgnoreCase(keyword)
                .or(admin.email.containsIgnoreCase(keyword));
    }

    // role 필터 조건
    private BooleanExpression roleEq(AdminRoleEnum role, QAdmin admin) {
        return role == null ? null : admin.role.eq(role);
    }

    // status 필터 조건
    private BooleanExpression statusEq(AdminStatusEnum status, QAdmin admin) {
        return status == null ? null : admin.status.eq(status);
    }

    // Pageable의 Sort 정보를 Querydsl OrderSpecifier 배열로 변환함
    private OrderSpecifier<?>[] toOrderSpecifiers(Sort sort, QAdmin admin) {
        if (sort == null || sort.isUnsorted()) {
            return new OrderSpecifier<?>[]{admin.createdAt.desc()};
        }

        List<OrderSpecifier<?>> order = new ArrayList<>();
        for (Sort.Order o : sort) {
            boolean asc = o.isAscending();
            switch (o.getProperty()) {
                case "name" -> order.add(asc ? admin.name.asc() : admin.name.desc());
                case "email" -> order.add(asc ? admin.email.asc() : admin.email.desc());
                case "createdAt" -> order.add(asc ? admin.createdAt.asc() : admin.createdAt.desc());
                case "approvedAt" -> order.add(asc ? admin.approvedAt.asc() : admin.approvedAt.desc());
                default -> order.add(admin.createdAt.desc());
            }
        }
        return order.toArray(new OrderSpecifier<?>[0]);
    }
}
