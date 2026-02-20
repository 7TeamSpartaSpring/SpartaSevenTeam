package com.seventeamproject.api.review.repository;

import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Page<ReviewResponse> search(Pageable pageable, Long id, String predicate);
}
