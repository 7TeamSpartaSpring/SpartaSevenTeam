package com.seventeamproject.api.review.service;

import com.seventeamproject.api.review.dto.ProductReviewSummaryResponse;
import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.dto.ReviewUpdateRequest;
import com.seventeamproject.api.review.entity.Review;
import com.seventeamproject.api.review.repository.ReviewRepository;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.MemberException;
import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getAll(Pageable pageable, String keyword, Integer rating) {
        return new PageResponse<>(reviewRepository.search(pageable, keyword, rating));
    }

    @Transactional(readOnly = true)
    public ReviewResponse getOne(Long id) {
        return new ReviewResponse(reviewRepository.findById(id).orElseThrow(
                () -> new MemberException(ErrorCode.REVIEW_NOT_FOUND)
        ));
    }

    public ReviewResponse update(Long id, ReviewUpdateRequest request) {
        return new ReviewResponse(reviewRepository.findById(id).
                orElseThrow(()-> new MemberException(ErrorCode.REVIEW_NOT_FOUND)).update(request.content()));
    }


    public void delete(Long id, PrincipalUser user) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new MemberException(ErrorCode.REVIEW_NOT_FOUND)
        );
        review.delete(user.getId());
    }


    public void restore(Long productId) {
        Review review = reviewRepository.findIncludeDeletedById(productId)
                .orElseThrow(() -> new MemberException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.isDeleted()) {
            throw new IllegalStateException("이미 복구되어 있는 리뷰입니다.");
        }

        review.restore();
    }

    public ProductReviewSummaryResponse getReviewSummary(Long productId) {

        List<Review> allReviews = reviewRepository.findByProduct_Id(productId);


        long totalCount = allReviews.size();
        double avgRating = allReviews.stream()
                .mapToLong(Review::getRating)
                .average()
                .orElse(0.0);
        avgRating = Math.round(avgRating * 10) / 10.0;


        Map<Integer, Long> starCounts = allReviews.stream()
                .collect(Collectors.groupingBy(
                        review -> review.getRating().intValue(),
                        Collectors.counting()
                ));

        IntStream.rangeClosed(1, 5).forEach(i -> starCounts.putIfAbsent(i, 0L));


        List<ReviewResponse> latestReviews = reviewRepository.findTop3ByProductIdOrderByCreatedAtDesc(productId)
                        .stream()
                        .map(ReviewResponse::new)
                        .toList();
        return new ProductReviewSummaryResponse(avgRating, totalCount, starCounts, latestReviews);
        }
}

