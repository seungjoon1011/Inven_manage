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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String create(@Valid @ModelAttribute WarehouseDto dto, RedirectAttributes redirectAttributes){
        warehouseService.create(dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
        return "redirect:/warehouses"; // 상품 목록 페이지로 리다이렉트
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("warehouseDto", new WarehouseDto());

        return "warehouseCreate";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 서비스에서 해당 ID의 상품 정보를 조회
        Warehouse warehouse = warehouseService.getById(id); // ProductService에 findById 메서드가 필요합니다.

        if (warehouse == null) {

            return "redirect:/warehouses";
        }

        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setName(warehouse.getName());
        warehouseDto.setLocation(warehouse.getLocation());

        model.addAttribute("warehouseDto", warehouseDto);
        model.addAttribute("warehouseId", id);

        return "warehouseEdit";
    }
    @PostMapping("/{id}")
    public String updateWarehouse(@PathVariable Long id,
                                @Valid @ModelAttribute WarehouseDto dto,
                                RedirectAttributes redirectAttributes) {
        warehouseService.update(id, dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다!");
        return "redirect:/warehouses";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        warehouseService.delete(id);
        return "redirect:/warehouses";
    }
}
