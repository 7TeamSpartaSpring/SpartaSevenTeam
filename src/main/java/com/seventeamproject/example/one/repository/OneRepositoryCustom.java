package com.seventeamproject.example.one.repository;

import com.seventeamproject.example.one.dto.OnesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OneRepositoryCustom {
    Page<OnesResponse> search(Pageable pageable, String title);
}
