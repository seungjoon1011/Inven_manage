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

            // productService.getAll() 메서드에 Pageable 인자를 넘겨줍니다.
            // 이 메서드는 Page<Product>를 반환합니다.
            Page<Product> productPage = productService.getAll(pageable);

            // 템플릿에서 사용할 데이터를 모델에 추가합니다.
            // "productPage"라는 이름으로 Page<Product> 객체를 넘겨줍니다.
            model.addAttribute("products", productPage);
        return "product";
    }
    @PostMapping
    public String create(@Valid @ModelAttribute ProductDto dto,RedirectAttributes redirectAttributes){
         productService.create(dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
        return "redirect:/products"; // 상품 목록 페이지로 리다이렉트
    }
//    @PostMapping("/products") // 폼의 th:action과 일치해야 합니다.
//    public String createProduct(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
//        // ProductDto에서 데이터를 추출하여 JPA 엔티티로 변환 후 저장
//        // 예시:
//        Product product = new Product();
//        product.setName(productDto.getName());
//        product.setPrice(productDto.getPrice());
//        productService.saveProduct(product); // ProductService를 통해 DB에 저장
//
//        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
//        return "redirect:/products"; // 상품 목록 페이지로 리다이렉트
//    }
//    @GetMapping
//    public Page<Product> List(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ){
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//
//        return productService.getAll(pageable);
//    }
    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("productDto", new ProductDto());

        return "productCreate";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 서비스에서 해당 ID의 상품 정보를 조회
        Product product = productService.getById(id); // ProductService에 findById 메서드가 필요합니다.

        if (product == null) {
            // 해당 ID의 상품이 없을 경우 처리 (예: 에러 페이지 또는 목록으로 리다이렉트)
            // 여기서는 간단히 상품 목록으로 리다이렉트합니다.
            return "redirect:/products";
        }

        // Product 엔티티의 데이터를 ProductDto에 복사하여 폼에 바인딩
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        // Product 엔티티에 다른 필드가 있다면 여기에 추가적으로 복사 로직을 넣어주세요.

        model.addAttribute("productDto", productDto); // 폼에 바인딩할 DTO 객체
        model.addAttribute("productId", id); // 폼 액션 URL에 사용할 상품 ID

        return "productEdit"; // productEdit.html 템플릿 반환
    }
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute ProductDto dto, // @RequestBody 대신 @ModelAttribute
                                RedirectAttributes redirectAttributes) {
        // ProductService의 update 메서드가 ProductDto와 ID를 받아서 처리한다고 가정
        productService.update(id, dto);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다!");
        return "redirect:/products"; // 수정 후 해당 상품의 상세 페이지 (또는 목록)로 리다이렉트
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }

}
