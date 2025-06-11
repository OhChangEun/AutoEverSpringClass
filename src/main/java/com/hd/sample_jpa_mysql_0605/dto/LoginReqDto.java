package com.hd.sample_jpa_mysql_0605.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginReqDto {
    private String email;
    private String pwd;
}
