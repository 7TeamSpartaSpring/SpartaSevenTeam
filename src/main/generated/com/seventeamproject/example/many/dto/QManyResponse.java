package com.seventeamproject.example.many.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seventeamproject.example.many.dto.QManyResponse is a Querydsl Projection type for ManyResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QManyResponse extends ConstructorExpression<ManyResponse> {

    private static final long serialVersionUID = 667080712L;

    public QManyResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> oneId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> value, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedAt) {
        super(ManyResponse.class, new Class<?>[]{long.class, long.class, String.class, long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, id, oneId, content, value, createdAt, modifiedAt);
    }

}

