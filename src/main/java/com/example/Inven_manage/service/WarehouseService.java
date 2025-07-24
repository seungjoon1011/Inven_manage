package com.example.Inven_manage.service;

import com.example.Inven_manage.dto.ProductDto;
import com.example.Inven_manage.dto.WarehouseDto;
import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.model.Warehouse;
import com.example.Inven_manage.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public Warehouse create(WarehouseDto dto){
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        return warehouseRepository.save(warehouse);
    }
    public Page<Warehouse> getAll(Pageable pageable) {
        return warehouseRepository.findAll(pageable);
    }
    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("창고를 찾을 수 없습니다. id=" + id));
    }
    public void delete(Long id){warehouseRepository.deleteById(id);}
    public Warehouse update(Long id, WarehouseDto dto) {
        Warehouse warehouse = getById(id);
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        return warehouseRepository.save(warehouse);
    }
}
