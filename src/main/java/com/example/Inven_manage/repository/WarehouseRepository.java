package com.example.Inven_manage.repository;

import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
