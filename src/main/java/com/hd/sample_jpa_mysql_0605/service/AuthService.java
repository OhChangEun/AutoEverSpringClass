package com.hd.sample_jpa_mysql_0605.service;

import com.hd.sample_jpa_mysql_0605.dto.SignUpReqDto;
import com.hd.sample_jpa_mysql_0605.entity.Member;
import com.hd.sample_jpa_mysql_0605.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j // Lombok: 로그 출력을 위한 어노테이션 (log.info(), log.error() 등 사용 가능)
@Service // Spring: 해당 클래스를 Service 계층의 Bean으로 등록
@Transactional // Spring: 메서드 실행 중 예외 발생 시 전체 작업을 롤백(트랜잭션 처리)
@RequiredArgsConstructor // Lombok: final이 붙은 필드에 대해 생성자 자동 생성
public class AuthService {
//    tomcat, servlet >> handle mapper >> Controller: ReqDTO >> Service:DTO -> Entity >> Repository: save() >> DB

    // final: 불변 객체로 설정
    private final MemberRepository memberRepository; // 생성자를 통한 의존성 주입 받음

    // 회원 가입 여부 확인
    public boolean isMember(String email){
        return memberRepository.existsByEmail(email);
    }
    // 회원 가입
    public boolean signup(SignUpReqDto signUpReqDto) {
        try {
            Member member = convertDtoToEntity(signUpReqDto);
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.error("회원가입 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }

    // 로그인
    public boolean login(String email, String pwd) {
        Optional<Member> member = memberRepository.findByEmailAndPwd(email, pwd);
        // Spring Data JPA가 메서드명을 기반으로 쿼리를 자동 생성함
        // ⇒ SELECT * FROM member WHERE email = ? AND pwd = ?
        // 일치하는 회원이 있으면 Optional에 감싸져 리턴됨
        // 없으면 Optional.empty()
        return member.isPresent(); // Optional에 값이 존재하면 true, 아니면 false
    }

    // DTO > Entity Mapping
    // Service 내부에서 사용하기 위함
    private Member convertDtoToEntity(SignUpReqDto signUpReqDto) {
        Member member = new Member();
        member.setEmail(signUpReqDto.getEmail());
        member.setPwd(signUpReqDto.getPwd());
        member.setName(signUpReqDto.getName());
        // @PrePersist를 통해 시간은 자동 입력 
        return member;
    }
}
