package com.seventeamproject.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSoftDeletableEntity is a Querydsl query type for SoftDeletableEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QSoftDeletableEntity extends EntityPathBase<SoftDeletableEntity> {

    private static final long serialVersionUID = 1020415059L;

    public static final QSoftDeletableEntity softDeletableEntity = new QSoftDeletableEntity("softDeletableEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> deletedBy = createNumber("deletedBy", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final NumberPath<Long> modifiedBy = _super.modifiedBy;

    public QSoftDeletableEntity(String variable) {
        super(SoftDeletableEntity.class, forVariable(variable));
    }

    public QSoftDeletableEntity(Path<? extends SoftDeletableEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSoftDeletableEntity(PathMetadata metadata) {
        super(SoftDeletableEntity.class, metadata);
    }

}

