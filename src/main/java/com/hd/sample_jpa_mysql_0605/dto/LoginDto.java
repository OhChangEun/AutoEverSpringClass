package com.hd.sample_jpa_mysql_0605.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString // toString() 메서드를 자동으로 생성
public class LoginDto {
    private String email;
    private String pwd;
}
