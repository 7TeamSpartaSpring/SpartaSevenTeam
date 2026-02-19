package com.seventeamproject.example.many.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.example.many.entity.Many;
import com.seventeamproject.example.one.dto.OneResponse;

import java.time.LocalDateTime;

public record ManyResponse(
        Long id,
        Long oneId,
        String content,
        Long value,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
    public ManyResponse(Many many) {
        this(many.getId(), many.getOne().getId(), many.getContent(), many.getValue(), many.getCreatedAt(), many.getModifiedAt());
    }
    @QueryProjection
    public ManyResponse {
    }
}
