package com.hd.sample_jpa_mysql_0605.controller;

import com.hd.sample_jpa_mysql_0605.dto.MemberReqDto;
import com.hd.sample_jpa_mysql_0605.dto.MemberResDto;
import com.hd.sample_jpa_mysql_0605.repository.MemberRepository;
import com.hd.sample_jpa_mysql_0605.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원 전체 조회
    @GetMapping("/all") // 정보가 브라우저 주소창에 보여짐
    public ResponseEntity<List<MemberResDto>> loadAllInfo() {
        return ResponseEntity.ok(memberService.findAll());
    }

    // 회원 상세 조회
    @GetMapping("/{email}")
    public ResponseEntity<MemberResDto> loadInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.findByEmail(email));
    }

    // 회원 정보 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> modifyInfo(@RequestBody MemberReqDto memberReqDto) {
        boolean isModified = memberService.modifyMember(memberReqDto);
        return ResponseEntity.ok(isModified);
    }

    // 회원 삭제
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Boolean> deleteInfo(@PathVariable String email) {
        boolean isDeleted = memberService.deleteMember(email);
        return ResponseEntity.ok(isDeleted);
    }
}
