package com.seventeamproject.example.many.repository;

import com.seventeamproject.example.many.dto.ManyResponse;
import com.seventeamproject.example.many.entity.Many;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManyRepository extends JpaRepository<Many, Long>, ManyRepositoryCustom {
    Page<ManyResponse> search(Pageable pageable, Long id, String predicate);
}
