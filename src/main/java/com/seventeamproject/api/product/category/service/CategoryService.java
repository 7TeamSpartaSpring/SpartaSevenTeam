package com.seventeamproject.api.product.category.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.dto.CategoryRequest;
import com.seventeamproject.api.product.category.dto.CategoryResponse;
import com.seventeamproject.api.product.category.entity.Category;
import com.seventeamproject.api.product.category.repository.CategoryRepository;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.example.one.dto.OnesResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;

    @Transactional
    public CategoryResponse save(CategoryRequest request, Long id) {
        return new CategoryResponse(categoryRepository.save(new Category(
                request.code(),
                request.name(),
                request.status(),
                entityManager.getReference(Admin.class, id)
        )));
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(category -> new CategoryResponse(category)).toList();
    }

    @Transactional
    public void delete(Long id, Long userId) {
        categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("삭제 할 카테고리가 없습니다")).delete(userId);
    }
}