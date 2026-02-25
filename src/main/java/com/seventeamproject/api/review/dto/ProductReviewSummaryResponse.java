package com.seventeamproject.api.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.common.dto.PageResponse;

import java.util.List;
import java.util.Map;

public record ProductReviewSummaryResponse(
        // [리뷰 통계 정보]
        Double averageRating,        // 평균 별점
        Long totalReviewCount,       //총 리뷰
        Map<Integer, Long> starCounts, // 별점당 리뷰 수
        // [최신 리뷰 목록]
        List<ReviewResponse> latestReviews // 최신 리뷰 3개
) {}
