package com.example.Inven_manage.repository;

import com.example.Inven_manage.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}