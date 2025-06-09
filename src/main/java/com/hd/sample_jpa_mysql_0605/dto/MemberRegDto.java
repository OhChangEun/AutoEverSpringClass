package com.hd.sample_jpa_mysql_0605.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegDto { // 회원 가입에 대한 요청
    private String email;
    private String pwd;
    private String name;
    private String image;
}
