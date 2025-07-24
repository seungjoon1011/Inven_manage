package com.example.Inven_manage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class WarehouseDto {
    @NotBlank(message = "창고명을 입력해주세요")
    private String name;

    @NotNull(message = "위치를 입력해주세요")
    private String location;
}
