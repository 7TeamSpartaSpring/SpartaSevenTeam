package com.seventeamproject.example.many.entity;

import com.seventeamproject.common.entity.SoftDeletableEntity;
import com.seventeamproject.example.one.entity.One;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "manys",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE schedules SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Many extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "one_id", nullable = false)
    private One one;

    private String content;

    private Long value;

    public Many(String content, Long value, One one) {
        this.content = content;
        this.value = value;
        this.one = one;
    }

    public Many update(String content, Long value) {
        this.content = content;
        this.value = value;
        return this;
    }

}
