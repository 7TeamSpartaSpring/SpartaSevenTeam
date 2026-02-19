package com.seventeamproject.example.one.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record OnesResponse (
        Long id,
        String title,
        String content,
        Long manyCount,
        Long manyValueSum,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    @QueryProjection
    public OnesResponse {
    }
}
