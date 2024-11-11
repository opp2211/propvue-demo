package ru.maltsev.propvuedemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProductRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private ProductStatus status;

    @NotBlank
    private String fulfilmentCenter;

    @NotNull
    @Positive
    private int quantity;

    @NotNull
    @Positive
    private BigDecimal price;
}
