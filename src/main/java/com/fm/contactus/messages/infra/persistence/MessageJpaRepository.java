package com.fm.contactus.messages.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<MessageJpaEntity, Long> {
	List<MessageJpaEntity> findByProjectId(Long projectId);

}
