package com.hd.sample_jpa_mysql_0605.service;

import com.hd.sample_jpa_mysql_0605.dto.MemberReqDto;
import com.hd.sample_jpa_mysql_0605.dto.SignUpReqDto;
import com.hd.sample_jpa_mysql_0605.dto.MemberResDto;
import com.hd.sample_jpa_mysql_0605.entity.Member;
import com.hd.sample_jpa_mysql_0605.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<MemberResDto> findAll() {
        List<Member> members = memberRepository.findAll(); // db의 정보를 member entity에 담음
        List<MemberResDto> memberResDtos = new ArrayList<>(); // dto list 생성

        for (Member member : members) {
            memberResDtos.add(convertEntityToDto(member));
        }
        return memberResDtos;
    }

    // 회원 상세 조회
    public MemberResDto findByEmail(String email) {
        try {
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재 하지 않습니다.")
            );
            return convertEntityToDto(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 회원 수정
    public boolean modifyMember(MemberReqDto memberReqDto) {
        try {
            Member member = memberRepository.findByEmail(memberReqDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재 하지 않습니다.")
            );
            member.setName(memberReqDto.getName());
            member.setPwd(memberReqDto.getPwd());
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.error("회원 정보 수정 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 회원 삭제
    public boolean deleteMember(String email) {
        try {
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재 하지 않습니다.")
            );
            memberRepository.delete(member);
            return true;
        } catch (Exception e) {
            log.error("회원 정보 삭제 실패 : {}", e.getMessage());
            return false;
        }
    }

    // entity를 dto에 담는 함수
    private MemberResDto convertEntityToDto(Member member) {
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(member.getEmail());
        memberResDto.setPwd(member.getPwd());
        memberResDto.setName(member.getName());
//      memberResDto.setImage(member.getImage());
        memberResDto.setRegDate(member.getRegDate());
        return memberResDto;
    }
}
