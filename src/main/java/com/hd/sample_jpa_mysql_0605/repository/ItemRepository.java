package com.hd.sample_jpa_mysql_0605.repository;

import com.hd.sample_jpa_mysql_0605.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Item 엔티티에 대한 데이터베이스 접근을 담당하는 인터페이스
// Item : 리포지토리가 관리할 엔티티 클래스
// Long : Item 엔티티의 기본 키(@Id) 타입
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> { // 기본적인 CRUD 기능과 페이징, 정렬 기능을 바로 사용
    // application-test.properties를 resources 아래에 생성 >> 테스트용 db
    // ItemRepository 우클릭 >> 이동 >> 테스트
    // test 폴더에 repository 아래에 ItemRepositoryTest 파일 자동 생성

    // 기본적인 CRUD는 이미 만들어져 있음
    List<Item> findByItemNm(String itemNm); // SELECT * FROM item WHERE item_nm = '';
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // @Query : JPQL,  nativeQuery, 직접 JPQL을 작성해서 실행할 수 있도록 해주는 어노테이션
    // JPQL : 객체 지향언어로 쿼리를 작성, 쿼리 메서드로 작성하기 힘든 복잡한 쿼리 작성 가능, JPQL 문법 숙지
    @Query("SELECT i FROM Item i WHERE i.itemDetail like %:itemDetail% ORDER BY i.price desc ")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // nativeQuery : 해당하는 DB의 실제 쿼리르 작성, 복잡한 쿼리 작성 가능ㄴ
    @Query(value = "SELECT * FROM item i WHERE i.item_detail " +
            "LIKE %:itemDetail% ORDER BY i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
