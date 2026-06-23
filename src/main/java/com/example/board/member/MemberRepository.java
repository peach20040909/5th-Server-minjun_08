package com.example.board.member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);   // 쿼리 메서드
    boolean existsByEmail(String email);
}
