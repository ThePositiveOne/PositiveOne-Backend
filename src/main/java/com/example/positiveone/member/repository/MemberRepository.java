package com.example.positiveone.member.repository;

import com.example.positiveone.member.domain.LoginType;
import com.example.positiveone.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberByLoginTypeAndLoginId(LoginType LoginType, String LoginId);
}
