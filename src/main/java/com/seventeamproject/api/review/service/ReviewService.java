package com.seventeamproject.api.review.service;

import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.dto.ReviewUpdateRequest;
import com.seventeamproject.api.review.repository.ReviewRepository;
import com.seventeamproject.common.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                () -> new IllegalStateException("존재하지 않는 리뷰입니다.")
        ));
    }

    public ReviewResponse update(Long id, ReviewUpdateRequest request) {
        return new ReviewResponse(reviewRepository.findById(id).
                orElseThrow(()-> new IllegalStateException("존재하지 않는 리뷰입니다.")).update(request.content()));
    }
}

