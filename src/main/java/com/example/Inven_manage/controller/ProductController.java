package com.example.Inven_manage.controller;

import com.example.Inven_manage.dto.ProductDto;
import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.service.ProductService;
import jakarta.servlet.http.HttpSession;
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

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public String list(
        @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
        Model model) {


            Page<Product> productPage = productService.getAll(pageable);

            model.addAttribute("products", productPage);
        return "product";
    }
    @PostMapping
    public String create(@Valid @ModelAttribute ProductDto dto,RedirectAttributes redirectAttributes){
         productService.create(dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
        return "redirect:/products"; // 상품 목록 페이지로 리다이렉트
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("productDto", new ProductDto());

        return "productCreate";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getById(id); // ProductService에 findById 메서드가 필요합니다.

        if (product == null) {

            return "redirect:/products";
        }

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());

        model.addAttribute("productDto", productDto);
        model.addAttribute("productId", id);

        return "productEdit";
    }
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute ProductDto dto,
                                RedirectAttributes redirectAttributes) {
        productService.update(id, dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다!");
        return "redirect:/products";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }

}
