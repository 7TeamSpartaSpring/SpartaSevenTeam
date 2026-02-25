package com.seventeamproject.api.review.repository;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.review.dto.ProductReviewSummaryResponse;
import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    @Query(value = "SELECT * FROM reviews WHERE id = :id", nativeQuery = true)
    Optional<Review> findIncludeDeletedById(@Param("id") Long id);

    Page<ProductReviewSummaryResponse> findByProduct_Id(Long productId, Pageable pageable);
    List<Review> findByProduct_Id (Long productId);
    List<Review> findTop3ByProductIdOrderByCreatedAtDesc(Long productId);
}
