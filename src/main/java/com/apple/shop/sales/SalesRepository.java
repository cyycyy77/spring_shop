package com.apple.shop.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
//    @Query(value="asdf", nativeQuery = true) // sql 문법 사용시
//    @Query(value="SELECT s FROM Sales s JOIN FETCH 컬럼명") // JPQL 문법 사용시
    @Query(value="SELECT s FROM Sales s JOIN FETCH s.member")
    List<Sales> customFindAll();
}
