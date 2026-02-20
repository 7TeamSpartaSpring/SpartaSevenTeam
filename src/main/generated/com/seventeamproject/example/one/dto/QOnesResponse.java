package com.seventeamproject.example.one.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seventeamproject.example.one.dto.QOnesResponse is a Querydsl Projection type for OnesResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOnesResponse extends ConstructorExpression<OnesResponse> {

    private static final long serialVersionUID = -272064901L;

    public QOnesResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> manyCount, com.querydsl.core.types.Expression<Long> manyValueSum, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedAt) {
        super(OnesResponse.class, new Class<?>[]{long.class, String.class, String.class, long.class, long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, id, title, content, manyCount, manyValueSum, createdAt, modifiedAt);
    }

}

