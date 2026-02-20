package com.seventeamproject.example.one.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOne is a Querydsl query type for One
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOne extends EntityPathBase<One> {

    private static final long serialVersionUID = -1670022983L;

    public static final QOne one = new QOne("one");

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

    public final StringPath title = createString("title");

    public QOne(String variable) {
        super(One.class, forVariable(variable));
    }

    public QOne(Path<? extends One> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOne(PathMetadata metadata) {
        super(One.class, metadata);
    }

}

