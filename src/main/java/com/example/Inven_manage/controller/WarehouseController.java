package com.example.Inven_manage.controller;

import com.example.Inven_manage.dto.ProductDto;
import com.example.Inven_manage.dto.WarehouseDto;
import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.model.Warehouse;
import com.example.Inven_manage.repository.WarehouseRepository;
import com.example.Inven_manage.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public String list(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
            Model model) {

        Page<Warehouse> warehousePage = warehouseService.getAll(pageable);

        model.addAttribute("warehouses", warehousePage);
        return "warehouse";
    }
    @PostMapping
    public Warehouse create(@Valid @RequestBody WarehouseDto dto){return warehouseService.create(dto);}
    @GetMapping("/{id}")
    public Warehouse get(@PathVariable Long id) {
        return warehouseService.getById(id);
    }
    @PutMapping("/{id}")
    public Warehouse update(@PathVariable Long id, @Valid @RequestBody WarehouseDto dto) {
        return warehouseService.update(id, dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        warehouseService.delete(id);
    }
}
