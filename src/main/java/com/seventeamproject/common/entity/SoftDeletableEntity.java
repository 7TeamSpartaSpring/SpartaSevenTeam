package com.seventeamproject.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@MappedSuperclass
public class SoftDeletableEntity extends BaseEntity {

    private Long deletedBy;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void delete(Long id) {
        this.deletedBy = id;
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedBy = null;
        this.deletedAt = null;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}