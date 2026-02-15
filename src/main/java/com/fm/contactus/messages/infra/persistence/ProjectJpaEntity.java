package com.fm.contactus.messages.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class ProjectJpaEntity {

    @Id
    @Column(name = "id")
    private Long id;

    protected ProjectJpaEntity() {
    }

    public Long getId() {
        return id;
    }
}
