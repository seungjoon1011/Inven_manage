package com.example.Inven_manage.controller;

import com.example.Inven_manage.dto.ProductDto;
import com.example.Inven_manage.dto.StockDto;
import com.example.Inven_manage.dto.viewDto;
import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.model.Stock;
import com.example.Inven_manage.repository.StockRepository;
import com.example.Inven_manage.repository.StockViewRepository;
import com.example.Inven_manage.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    private final StockViewRepository stockViewRepository;


    @GetMapping
    public String list(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
            Model model) {

        Page<Stock> stockPage = stockService.getAll(pageable);

        model.addAttribute("stocks", stockPage);
        return "stock";
    }
    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("stockDto", new StockDto());

        return "stockCreate";
    }
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute StockDto dto,RedirectAttributes redirectAttributes){
        stockService.create(dto);
        redirectAttributes.addFlashAttribute("message", "재고가 성공적으로 등록되었습니다!");
        return "redirect:/stocks";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 서비스에서 해당 ID의 상품 정보를 조회
        Stock stock = stockService.getById(id);
        StockDto stockDto = new StockDto();
        stockDto.setQuantity(stock.getQuantity());
        stockDto.setProductId(stock.getProduct().getId());
        stockDto.setWarehouseId(stock.getWarehouse().getId());

        model.addAttribute("stockDto", stockDto); // 폼에 바인딩할 DTO 객체
        model.addAttribute("stockId", id); // 폼 액션 URL에 사용할 상품 ID

        return "stockEdit";
    }
    @PostMapping("/edit/{id}")
    public String updateStock(@PathVariable Long id,
                              @Valid @ModelAttribute StockDto dto, // @RequestBody 대신 @ModelAttribute
                              RedirectAttributes redirectAttributes) {
        stockService.update(id, dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다!");
        return "redirect:/stocks";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        stockService.delete(id);
        return "redirect:/stocks";
    }
}
