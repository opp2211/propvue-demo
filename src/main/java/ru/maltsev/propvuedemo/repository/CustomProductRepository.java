package ru.maltsev.propvuedemo.repository;

import jakarta.annotation.Nullable;
import ru.maltsev.propvuedemo.model.ProductStatus;

import java.math.BigDecimal;

public interface CustomProductRepository {
    BigDecimal getTotalValueByStatusAndFfmtCenter(
            @Nullable ProductStatus status, @Nullable String ffmtCenter);
}
