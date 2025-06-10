package com.hd.sample_jpa_mysql_0605.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // JPA 엔티티라는 뜻: 데이터베이스 테이블로 사용될 거라는 표시
@Table(name = "member") // 이 엔티티가 매핑될 테이블 이름을 지정해요.
@Getter @Setter // 자동 get(), set() 메서드 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@ToString
public class Member {
    @Id // @Id: 이 필드는 **기본 키(PK)**예요. 각 회원을 구별하는 고유값.
    @GeneratedValue(strategy = GenerationType.AUTO) // @GeneratedValue: 값을 자동으로 생성해줘요. (AUTO는 DB에 맞게 전략 자동 선택)
    // 생성 전략
    @Column(name = "member_id") //  DB 컬럼 이름을 "member id"로 지정했는데, 공백은 피하는 게 좋아요 → member_id로 수정 권장
    private Long id;

    @Column(length = 100) // 이 설정이 없으면 기본 길이로 저장 (보통 255자)
    private String name;

    @Column(nullable = false) // 반드시 값이 있어야 함(= NOT NULL)
    private String pwd;

    @Column(unique = true, length = 150) // 중복 허용 안 됨 (예: 같은 이메일은 한 명만 사용 가능), 이메일 최대 150자까지 가능
    private String email;

    private LocalDateTime regDate;

    @PrePersist // DB에 INSERT 되기 전에 실행되는 메서드
    private void prePersist() {
        this.regDate = LocalDateTime.now(); // regDate가 자동으로 현재 시간으로 설정됩니다.
    }
}
