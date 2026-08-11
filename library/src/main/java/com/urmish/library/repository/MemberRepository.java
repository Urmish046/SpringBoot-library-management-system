package com.urmish.library.repository;

import com.urmish.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);
}
