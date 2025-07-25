package com.example.Inven_manage.service;

import com.example.Inven_manage.dto.StockDto;
import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.model.Stock;
import com.example.Inven_manage.model.Warehouse;
import com.example.Inven_manage.repository.ProductRepository;
import com.example.Inven_manage.repository.StockRepository;
import com.example.Inven_manage.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public Page<Stock> getAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }
    public Stock getById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재고를 찾을 수 없습니다."));
    }

    public Stock create(StockDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NoSuchElementException("창고를 찾을 수 없습니다."));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(dto.getQuantity());
        return stockRepository.save(stock);
    }

    public void delete(Long id) {
        stockRepository.deleteById(id);
    }
    public Stock update(Long id, StockDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NoSuchElementException("창고를 찾을 수 없습니다."));

        Stock stock = getById(id);
        stock.setQuantity(dto.getQuantity());
        stock.setProduct(product);
        stock.setWarehouse(warehouse);

        return stockRepository.save(stock);
    }
}
