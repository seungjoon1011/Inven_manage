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

        // warehouseService.getAll() 메서드에 Pageable 인자를 넘겨줍니다.
        // 이 메서드는 Page<Product>를 반환합니다.
        Page<Warehouse> warehousePage = warehouseService.getAll(pageable);

        // 템플릿에서 사용할 데이터를 모델에 추가합니다.
        // "warehousePage"라는 이름으로 Page<Product> 객체를 넘겨줍니다.
        model.addAttribute("warehouses", warehousePage);
        return "warehouse";
    }
    @PostMapping
    public String create(@Valid @ModelAttribute WarehouseDto dto, RedirectAttributes redirectAttributes){
        warehouseService.create(dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
        return "redirect:/warehouses"; // 상품 목록 페이지로 리다이렉트
    }
    //    @PostMapping("/warehouses") // 폼의 th:action과 일치해야 합니다.
//    public String createProduct(@ModelAttribute ProductDto warehouseDto, RedirectAttributes redirectAttributes) {
//        // ProductDto에서 데이터를 추출하여 JPA 엔티티로 변환 후 저장
//        // 예시:
//        Product warehouse = new Product();
//        warehouse.setName(warehouseDto.getName());
//        warehouse.setPrice(warehouseDto.getPrice());
//        warehouseService.saveProduct(warehouse); // ProductService를 통해 DB에 저장
//
//        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
//        return "redirect:/warehouses"; // 상품 목록 페이지로 리다이렉트
//    }
//    @GetMapping
//    public Page<Product> List(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ){
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//
//        return warehouseService.getAll(pageable);
//    }
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
            // 해당 ID의 상품이 없을 경우 처리 (예: 에러 페이지 또는 목록으로 리다이렉트)
            // 여기서는 간단히 상품 목록으로 리다이렉트합니다.
            return "redirect:/warehouses";
        }

        // Product 엔티티의 데이터를 ProductDto에 복사하여 폼에 바인딩
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setName(warehouse.getName());
        warehouseDto.setLocation(warehouse.getLocation());
        // Product 엔티티에 다른 필드가 있다면 여기에 추가적으로 복사 로직을 넣어주세요.

        model.addAttribute("warehouseDto", warehouseDto); // 폼에 바인딩할 DTO 객체
        model.addAttribute("warehouseId", id); // 폼 액션 URL에 사용할 상품 ID

        return "warehouseEdit"; // warehouseEdit.html 템플릿 반환
    }
    @PostMapping("/{id}")
    public String updateWarehouse(@PathVariable Long id,
                                @Valid @ModelAttribute WarehouseDto dto, // @RequestBody 대신 @ModelAttribute
                                RedirectAttributes redirectAttributes) {
        // ProductService의 update 메서드가 ProductDto와 ID를 받아서 처리한다고 가정
        warehouseService.update(id, dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다!");
        return "redirect:/warehouses"; // 수정 후 해당 상품의 상세 페이지 (또는 목록)로 리다이렉트
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        warehouseService.delete(id);
        return "redirect:/warehouses";
    }
}
