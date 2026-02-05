package com.example.db_test.native_test;

import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<MemberEntity, Long> {
    @Query(value = "select * from member_test", nativeQuery = true) // 사용자가 직접 지정한 쿼리로 동작
    List<MemberEntity> findAllList();
    @Query(value = "select * from member_test where number=:num", nativeQuery = true)
    Optional<MemberEntity> findByContent(@Param("num") long num);

    // 수정, 삭제 하게 된다면 (select 이외의 쿼리문)
    @Modifying
    @Transactional
    @Query(value = "insert to member_test(user_id, user_name, age) values(:userId, :userName, :age)",
            nativeQuery = true)
    int insertContent(
            @Param("userId") String userId,
            @Param("userName") String userName,
            @Param("age")int age
    );
}
