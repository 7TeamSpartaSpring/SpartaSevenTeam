package com.seventeamproject.example.one.repository;

import com.seventeamproject.example.one.dto.OnesResponse;
import com.seventeamproject.example.one.entity.One;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OneRepository extends JpaRepository<One, Long>, OneRepositoryCustom {
    Page<OnesResponse> search(Pageable pageable, String title);
}
