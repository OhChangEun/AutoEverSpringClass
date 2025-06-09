package com.hd.sample_jpa_mysql_0605.repository;

import com.hd.sample_jpa_mysql_0605.constant.ItemSellStatus;
import com.hd.sample_jpa_mysql_0605.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j // Log 메시지를 출력하기 위해서 사용하는 롬복의 어노테이션
@SpringBootTest // 스프링 부트를 사용하는 통합 테스트 환경을 구성
// 실제 애플리케이션처럼 스프링 컨텍스트를 통째로 실행함 (빈 자동 주입도 가능)
@TestPropertySource(locations = "classpath:application-test.properties")
// 테스트 실행 시 사용할 설정 파일(application-test.properties) 을 명시해주는 애너테이션
//기본 설정 파일인 application.properties나 application.yml 대신
//테스트 전용 DB, 설정값 등을 별도로 사용하고 싶을 때 설정함.
class ItemRepositoryTest {
    @Autowired // 의존성 주입(Dependency Injection)
    // 스프링이 필요한 객체(빈, Bean)를 자동으로 주입해줘.
    // 즉, 개발자가 직접 new로 객체를 만들지 않아도,스프링 컨테이너가 알아서 객체를 생성하고 넣어줌
    ItemRepository itemRepository; // 필드 의존성 주입을 받음
    //    public class ItemService {
    //        private ItemRepository itemRepository = new ItemRepository(); // 직접 생성
    //    }
    //    이렇게 직접 new로 객체를 생성하면,
    //    1) 코드가 딱딱하게 고정됨
    //    2) 테스트도 어렵고
    //    3) 객체 관리도 복잡함

        //    >> 미리 객체들을 생성하고(빈으로 등록하고), 필요한 곳에 자동으로 넣어줌 → 이게 “주입”이야.
    //    즉, 내가 new 하지 않아도 스프링이 대신 만들어서 넣어주는 것이야.

    @Test
    @DisplayName("상품 저장 테스트") // 테스트 시 출력되는 이름
    public void createTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        log.debug("Item : {}", savedItem);
    }

    public void createItemList() {
        for (int i=1; i<= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item); // 새로운 아이템 추가, insert
            log.debug("Item : {}", savedItem);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> list = itemRepository.findByItemNm("테스트 상품5"); // 값이 없다면 빈 리스트
//        if (list.isEmpty()) return;

        for (Item item : list) {
            log.info("Item: {}", item);
        }
    }

    @Test
    @DisplayName("상품명 or 상품상세 설명")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<Item> list = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 설명5");

        for (Item item : list) {
            log.info("item : {}", item);
        }
    }
    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> list = itemRepository.findByItemDetail("테스트 상품 설명5");
        for (Item item : list) {
            log.info("item : {}", item);
        }
    }

    @Test
    @DisplayName("native Query 테스트")
    public void findByItemDetailByNative() {
        this.createItemList();
        List<Item> list = itemRepository.findByItemDetailByNative("테스트 상품 설명8");
        for (Item item : list) {
            log.info("item : {}", item);
        }
    }

}