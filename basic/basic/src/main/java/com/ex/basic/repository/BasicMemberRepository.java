package com.ex.basic.repository;

import com.ex.basic.dto.LoginDto;
import com.ex.basic.dto.MemberDto;
import com.ex.basic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicMemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByUsername(String username);

    Optional<MemberEntity> findByUsername(String username);
}
