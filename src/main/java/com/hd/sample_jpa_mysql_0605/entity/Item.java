package com.hd.sample_jpa_mysql_0605.entity;

import com.hd.sample_jpa_mysql_0605.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity   // 데이터베이스 테이블과 매핑되는 "엔티티 클래스" 임을 나타냄
@Table(name="item")  // @Entity로 지정한 클래스가 어떤 데이터베이스 테이블에 매핑될지를 설정, 소문자로 생성
@Getter
@Setter
@ToString // toString() 오버라이드를 자동으로 해줌
public class Item {
    @Id // @Id: 이 필드는 ** 기본 키(PK) ** 예요. 각 회원을 구별하는 고유 값. 반드시 하나 있어야함.
    @Column(name="item_id") // DB로 생성될 컬럼 이름 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략, JPA가 DB에 맞게 생성 전략을 선택
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name="price", nullable = false)
    private int price;

    @Column(nullable = false) // stock_number, 스네이크 표기법으로 자동 변경
    private int stockNumber; // 재고 수량

    @Lob // 매핑하는 필드가 '대용량 객체'(Large Object, LOB)임을 지정
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING) // 자바의 enum 타입 필드를 데이터베이스 컬럼에 매핑
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
