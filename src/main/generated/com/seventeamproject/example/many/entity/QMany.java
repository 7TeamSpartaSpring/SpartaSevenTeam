package com.seventeamproject.example.many.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMany is a Querydsl query type for Many
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMany extends EntityPathBase<Many> {

    private static final long serialVersionUID = -1814842991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMany many = new QMany("many");

    public final com.seventeamproject.common.entity.QSoftDeletableEntity _super = new com.seventeamproject.common.entity.QSoftDeletableEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> deletedBy = _super.deletedBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final NumberPath<Long> modifiedBy = _super.modifiedBy;

    public final com.seventeamproject.example.one.entity.QOne one;

    public final NumberPath<Long> value = createNumber("value", Long.class);

    public QMany(String variable) {
        this(Many.class, forVariable(variable), INITS);
    }

    public QMany(Path<? extends Many> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMany(PathMetadata metadata, PathInits inits) {
        this(Many.class, metadata, inits);
    }

    public QMany(Class<? extends Many> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.one = inits.isInitialized("one") ? new com.seventeamproject.example.one.entity.QOne(forProperty("one")) : null;
    }

}

