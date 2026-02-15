package com.fm.contactus.messages.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<TokenJpaEntity, Long> {
    
    Optional<TokenJpaEntity> findByTokenEncoded(String encodedValue);
}
