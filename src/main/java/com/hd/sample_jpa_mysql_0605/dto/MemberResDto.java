package com.hd.sample_jpa_mysql_0605.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 매개변수가 전부 있는 생성자, 역직렬화 시 반드시 필요
// @Data // 모든 어노테이션을 한번에 적용
@ToString // toString() 메서드를 자동으로 생성
public class MemberResDto {
    private String email;
    private String pwd;
    private String name;
    private String image;
    private LocalDateTime regDate;
}
