package com.hd.sample_jpa_mysql_0605.controller;

import com.hd.sample_jpa_mysql_0605.dto.LoginDto;
import com.hd.sample_jpa_mysql_0605.dto.MemberRegDto;
import com.hd.sample_jpa_mysql_0605.dto.MemberResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j // Log 메시지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/signup")
    public ResponseEntity<MemberResDto> signup(@RequestBody MemberRegDto memberRegDto) {
        log.info("member : {}", memberRegDto);
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(memberRegDto.getEmail());
        memberResDto.setName(memberRegDto.getName());
        memberResDto.setPwd(memberRegDto.getPwd());
        memberResDto.setImage("/test/test.img");
        memberResDto.setRegDate(LocalDateTime.now());
        return ResponseEntity.ok(memberResDto);
    }
    
    // 로그인
    // Post 방식 : email, pwd를 Request Body 형식으로 수신
    // 응답은 boolean 값으로 응답 (true / false)
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDto loginDto) {
        log.info("member : {}", loginDto);

        // mock email/pwd
        String mockEmail = "test@example.com";
        String mockPwd = "1234";

        boolean isAuthenticated = loginDto.getEmail().equals(mockEmail)
                && loginDto.getPwd().equals(mockPwd);

        return ResponseEntity.ok(isAuthenticated);
    }
    // 회원 조회
    // Get 방식: email이 있으면 해당 회원 조회
    // 없으면 전체 회원 조회
    // 단, 회원 정보 리스트는 서비스 로직과 DB가 없으므로. for문으로 자체 생성

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@RequestParam(required = false) String email) {
        List<MemberResDto> memberList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            MemberResDto member = new MemberResDto();
            member.setEmail("hong" + i + "@example.com");
            member.setName("홍길동" + i);
            member.setPwd("pass" + i);
            member.setImage("/images/hong" + i + ".jpg");
            member.setRegDate(LocalDateTime.now());
            memberList.add(member);
        }

        // 특정 이메일이 있는 경우 해당 회원 반환
        if (email != null) {
            for (MemberResDto member : memberList) {
                if (member.getEmail().equals(email)) {
                    return ResponseEntity.ok(member);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 이메일의 회원을 찾을 수 없습니다: " + email);
        }
        // 이메일이 없으면 전체 회원 반환
        return ResponseEntity.ok(memberList);
    }
}
