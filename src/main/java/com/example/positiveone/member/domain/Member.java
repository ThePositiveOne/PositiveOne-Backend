package com.example.positiveone.member.domain;

import com.example.positiveone.global.config.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;
    private String name;
    private int reportCnt;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String loginId;

    private String refreshToken;

    public void reportIncrease(){
        this.reportCnt++;
    }

    public void deleteMember(Long memberId){
        this.email = memberId + "@positive.com";
        this.loginId = String.valueOf(memberId);
        this.loginType = LoginType.POSITIVE_ONE;
        this.refreshToken = "none";
    }
}
