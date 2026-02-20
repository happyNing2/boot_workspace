package com.ex.basic.repository;

import com.ex.basic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicMemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByUsername(String username);

    Optional<MemberEntity> findByUsername(String username);
    Optional<MemberEntity> getReferenceByUsername(String username);
}
