package com.seventeamproject.example.one.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.example.many.entity.QMany;
import com.seventeamproject.example.one.dto.OnesResponse;
import com.seventeamproject.example.one.dto.QOnesResponse;
import com.seventeamproject.example.one.entity.QOne;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class OneRepositoryImpl implements OneRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OnesResponse> search(Pageable pageable, String title) {

        QOne one = QOne.one;
        QMany many = QMany.many;

        List<OnesResponse> content = queryFactory
                .select(new QOnesResponse(
                        one.id,
                        one.title,
                        one.content,
                        JPAExpressions
                                .select(many.count())
                                .from(many)
                                .where(many.one.id.eq(one.id)),
                        JPAExpressions
                                .select(many.value.sum())
                                .from(many)
                                .where(many.one.id.eq(one.id)),
                        one.createdAt,
                        one.modifiedAt
                ))
                .from(one)
//                .leftJoin(user).on(one.userId.eq(user.id))
                .where(titleContains(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(one.count())
                .from(one)
                .where(titleContains(title))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QOne.one.title.contains(title) : null;
    }
}