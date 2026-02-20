package com.seventeamproject.api.review.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.review.dto.QReviewResponse; // 생성된 QDTO
import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.seventeamproject.api.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponse> search(Pageable pageable, Long productId, String keyword) {

        List<ReviewResponse> content = queryFactory
                .select(new QReviewResponse(
                        review.id,
                        review.product,
                        review.orderId,
                        review.customer,
                        review.rating,
                        review.content,
                        review.createdAt
                ))
                .from(review)
                .where(
                        productEq(productId),
                        contentContains(keyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        productEq(productId),
                        contentContains(keyword)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private BooleanExpression productEq(Long productId) {
        return productId != null ? review.product.eq(productId) : null;
    }

    private BooleanExpression contentContains(String keyword) {
        return keyword != null ? review.content.contains(keyword) : null;
    }
}
