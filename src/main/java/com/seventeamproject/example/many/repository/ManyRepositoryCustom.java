package com.seventeamproject.example.many.repository;

import com.seventeamproject.example.many.dto.ManyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManyRepositoryCustom {
    Page<ManyResponse> search(Pageable pageable, Long id, String predicate);
}