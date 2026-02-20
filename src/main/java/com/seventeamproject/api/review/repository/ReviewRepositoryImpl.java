package com.seventeamproject.api.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.review.dto.QReviewResponse;
import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponse> search(Pageable pageable, String keyword, Integer rating) {
        QReview qReview = QReview.review;


        List<ReviewResponse> content = queryFactory
                .select(new QReviewResponse(
                        qReview.id,
                        qReview.product,
                        qReview.order,
                        qReview.customer,
                        qReview.reviewQty,
                        qReview.rating,
                        qReview.content,
                        qReview.createdAt
                ))
                .from(qReview)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(qReview.count())
                .from(qReview)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}