package com.apple.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable pageable);
    List<Item> findByTitleContains(String title);

//    @Query(value = "select * from item where id = ?1", nativeQuery = true)
////    sql query문에도 파라미터 문법 사용가능
//    Item rawQuery1(Long num);

    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    List<Item> rawQuery1(String text);
    //native query는 쌩으로 query문 짜도록 해주는 거구나
    //against가 full text query
    //괄호 안의 parameter(text)가 Query의 ?1 자리에 들어간다.

    List<Item> findByUserid(String userid);
}
