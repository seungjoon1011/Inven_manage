package com.example.Inven_manage.repository;

import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
