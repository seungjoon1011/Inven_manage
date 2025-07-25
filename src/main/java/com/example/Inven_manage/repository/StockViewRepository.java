package com.example.Inven_manage.repository;

import com.example.Inven_manage.dto.viewDto;
import com.example.Inven_manage.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockViewRepository extends JpaRepository<Stock, Long> {
    // 네이티브 쿼리를 사용하여 product_stock_summary 뷰의 데이터를 조회하고 DTO에 매핑합니다.
    // DTO의 필드명과 뷰의 컬럼명을 일치시키거나, AS 키워드를 사용하여 매핑합니다.
    @Query(value = "SELECT p.id AS product_id, p.name AS product_name, " +
            "w.id AS warehouse_id, w.name AS warehouse_name, s.quantity " +
            "FROM stocks s " +
            "JOIN products p ON p.id = s.product_id " +
            "JOIN warehouses w ON w.id = s.warehouse_id " +
            "ORDER BY p.id, w.id",
            nativeQuery = true)
    List<viewDto> findAllsm();
}