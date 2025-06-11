package com.hd.sample_jpa_mysql_0605.repository;

import com.hd.sample_jpa_mysql_0605.constant.ItemSellStatus;
import com.hd.sample_jpa_mysql_0605.entity.Item;
import com.hd.sample_jpa_mysql_0605.entity.Order;
import com.hd.sample_jpa_mysql_0605.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OrderRepositoryTest {
//    @Autowired
//    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return item;
    }
    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            //아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담아줍니다.
            order.getOrderItemList().add(orderItem);
        }
        // order 엔티티를 저장하면서 강제로 flush를 호출하여 영속성 컨텍스트 반영
        orderRepository.saveAndFlush(order);
        // 영속성 상태를 초기화
        em.clear();
//        // 주문 엔티티 조회
//        Order saveOrder = orderRepository.findById(order.getId())
//                .orElseThrow(EntityNotFoundException::new);
//        assertEquals(3, saveOrder.getOrderItemList().size());
    }
}