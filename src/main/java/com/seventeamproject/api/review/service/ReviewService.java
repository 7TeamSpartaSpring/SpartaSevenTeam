package com.seventeamproject.api.review.service;

import com.seventeamproject.api.review.dto.ReviewResponse;
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

    public PageResponse<ReviewResponse> getAll(Pageable pageable, String keyword, Integer rating) {
        return new PageResponse<>(reviewRepository.search(pageable, keyword, rating));
    }
}

