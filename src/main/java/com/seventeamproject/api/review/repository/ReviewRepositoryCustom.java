package com.seventeamproject.api.review.repository;

import com.seventeamproject.api.review.dto.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewResponse> search(Pageable pageable, Long id, String predicate);
}