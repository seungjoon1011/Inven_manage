package com.example.Inven_manage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class viewDto {
    private Long productId;
    private String productName;
    private Long warehouseId;
    private String warehouseName;
    private int quantity; // 재고 수량
}