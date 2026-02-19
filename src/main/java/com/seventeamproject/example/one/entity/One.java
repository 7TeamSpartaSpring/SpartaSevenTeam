package com.seventeamproject.example.one.entity;


import com.seventeamproject.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "ones",
        indexes = {
                @Index(name = "idx_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE schedules SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class One extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    public One(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public One update(String title, String content) {
        this.title = title;
        this.content= content;
        return this;
    }

}
