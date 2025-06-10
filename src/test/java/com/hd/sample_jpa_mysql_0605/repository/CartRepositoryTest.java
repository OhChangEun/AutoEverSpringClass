package com.hd.sample_jpa_mysql_0605.repository;

import com.hd.sample_jpa_mysql_0605.entity.Cart;
import com.hd.sample_jpa_mysql_0605.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional // 여러 개의 작업을 한개의 논리적인 잔적 단위로 묶어 줌, 테스트 성공 / 실패와 상관없이 자동 롤백
@TestPropertySource(locations = "classpath:application-test.properties")
class CartRepositoryTest {
    @Autowired // 의존성 주입, 생성자를 통한 의존성 주입인 경우는 어노테이션 필요 없음
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    // 회원 엔티티 생성
    public Member createMemberInfo() {
        Member member = new Member();
        member.setEmail("dwc07109@naver.com");
        member.setName("오창창");
        member.setPwd("1234");
        member.setRegDate(LocalDateTime.now());
        return member;
    }
    @Test
    @DisplayName("장바구니와 회원 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMemberInfo();
        memberRepository.save(member); // DB에 회원 추가

        Cart cart = new Cart();
        cart.setCartName("자동차 구매 장바구니");
        cart.setMember(member);
        cartRepository.save(cart);

        // em은 EntityManager입니다 (JPA 핵심 객체)
        em.flush();
        // 영속성 컨텍스트에 저장된 내용을 DB에 강제로 반영 (즉시 INSERT/UPDATE 실행)
        // 아직 DB에 반영되지 않은 상태를 DB에 저장
        // 현재 스레드에는 트랜잭션이 활성화되어 있지 않기 때문에,
        // EntityManager.flush()를 안전하게 수행할 수 없음
        // >> @Transactional이 붙어 있어야 EntityManager가 실제 트랜잭션 환경에서 동작
        // ✔️ EntityManager는 "쓰기 작업"을 할 때는 반드시 트랜잭션 환경에서 실행되어야 합니다.
        // ❌ 단순 조회(read-only)는 트랜잭션 없이도 일부 가능합니다.
        em.clear();
        // 현재 영속성 컨텍스트를 초기화 (캐시 비우기)
        // 이후 find() 등의 조회 시 DB에서 직접 조회하게 됨
        // >> 캐시가 아닌 실제 DB 상태를 확인하려고 하는 것

        Cart saveCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);
        log.info("Cart: {}", saveCart);
    }
}