package ru.maltsev.propvuedemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maltsev.propvuedemo.model.ProductStatus;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private ProductStatus status;
    private String fulfilmentCenter;
    private int quantity;
    private BigDecimal value;
}
