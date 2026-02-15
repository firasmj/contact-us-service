package com.fm.contactus.messages.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {
}
