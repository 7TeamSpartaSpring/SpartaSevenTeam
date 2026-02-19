package com.seventeamproject.example.many.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.example.many.dto.ManyResponse;
import com.seventeamproject.example.many.dto.QManyResponse;
import com.seventeamproject.example.many.entity.QMany;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ManyRepositoryImpl implements ManyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ManyResponse> search(Pageable pageable, Long id, String predicate ) {
        QMany many = QMany.many;

        List<ManyResponse> content = queryFactory
                .select(new QManyResponse(
                        many.id,
                        many.one.id,
                        many.content,
                        many.value,
                        many.createdAt,
                        many.modifiedAt
                ))
                .from(many)
//                .leftJoin(user).on(one.userId.eq(user.id))
                .where(many.one.id.eq(id).and(contentContains(predicate)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(many.count())
                .from(many)
                .where(contentContains(predicate))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? QMany.many.content.contains(content) : null;
    }
}
