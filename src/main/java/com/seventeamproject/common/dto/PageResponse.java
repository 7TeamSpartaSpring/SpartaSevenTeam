package com.seventeamproject.common.dto;

import org.springframework.data.domain.Page;
import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int number,
        int size,
        long totalPages,
        long totalElements,
        boolean isLast
) {
    public PageResponse(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast()
        );
    }
}
