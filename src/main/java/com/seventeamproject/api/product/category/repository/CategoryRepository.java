package com.seventeamproject.api.product.category.repository;

import com.seventeamproject.api.product.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM categorys WHERE deleted_at IS NOT NULL", nativeQuery = true)
    List<Category> findAllByDeletedAtIsNotNull();
}
