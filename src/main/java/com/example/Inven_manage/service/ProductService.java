package com.example.Inven_manage.service;

import com.example.Inven_manage.dto.ProductDto;
import com.example.Inven_manage.model.Product;
import com.example.Inven_manage.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public Product create(ProductDto dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다. id=" + id));
    }
    public void delete(Long id){
        productRepository.deleteById(id);
    }
    public Product update(Long id, ProductDto dto) {
        Product product = getById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }
}
