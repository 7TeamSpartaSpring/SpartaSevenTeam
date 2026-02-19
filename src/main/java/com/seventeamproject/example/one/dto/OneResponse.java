package com.seventeamproject.example.one.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.example.many.dto.ManyResponse;
import com.seventeamproject.example.one.entity.One;

import java.time.LocalDateTime;

public record OneResponse(
        Long id,
        String title,
        String content,
        PageResponse<ManyResponse> manys,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public OneResponse(One one) {
        this(one.getId(), one.getTitle(), one.getContent(), null, one.getCreatedAt(), one.getModifiedAt());
    }
    public OneResponse(One one, PageResponse<ManyResponse> manys) {
        this(one.getId(), one.getTitle(), one.getContent(), manys, one.getCreatedAt(), one.getModifiedAt());
    }
}