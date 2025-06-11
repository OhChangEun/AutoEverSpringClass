package com.hd.sample_jpa_mysql_0605.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberReqDto {
    private String email;
    private String pwd;
    private String name;
    private String image;
}
