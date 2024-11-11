package ru.maltsev.propvuedemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.maltsev.propvuedemo.dto.ProductRequestDto;
import ru.maltsev.propvuedemo.dto.ProductResponseDto;
import ru.maltsev.propvuedemo.model.ProductStatus;
import ru.maltsev.propvuedemo.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Продукты", description = "Контроллер для работы с продуктами")
@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Добавление нового продукта",
            description = "Позволяет добавить новый продукт"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto addNew(@Validated @RequestBody ProductRequestDto productDto) {
        return productService.addNew(productDto);
    }

    @Operation(summary = "Получение продукта",
            description = "Позволяет получить продукт по его ID"
    )
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable @Positive long id) {
        return productService.getById(id);
    }

    @Operation(summary = "Получение списка продуктов",
            description = "Позволяет получить все продукты, а также отфильтровать их по статусу"
    )
    @GetMapping
    public List<ProductResponseDto> getAll(
            @Parameter(description = "Статус продуктов, допустимо NULL (выгрузка всех)")
            @RequestParam(required = false) ProductStatus status) {
        return productService.getAll(status);
    }

    @Operation(summary = "Обновление продукта",
            description = "Позволяет обновить данные продукта по ID перезаписью - необходимо передать старые данные, если они должны остаться без изменений"
    )
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable @Positive Long id,
                                     @RequestBody @Validated ProductRequestDto productDto) {
        return productService.update(id, productDto);
    }

    @Operation(summary = "Удаление продукта",
            description = "Позволяет удалить продукт по его ID"
    )
    @DeleteMapping("/{id}")
    public ProductResponseDto remove(@PathVariable @Positive Long id) {
        return productService.remove(id);
    }

    @Operation(summary = "Получение общего значения",
            description = "Позволяет суммарную стоимость всех продуктов, а также по статусу и/или центру выполнения"
    )
    @GetMapping("/total-value")
    public BigDecimal getTotalValueOfProducts(
            @Parameter(description = "Статус продукта, допустимо NULL (не фильтровать по статусу)")
            @RequestParam(required = false)
            ProductStatus status,
            @Parameter(description = "Название центра выполнения, допустимо NULL (не фильтровать по центру)")
            @RequestParam(required = false)
            String fulfilmentCenter) {

        return productService.getTotalValueOfProducts(status, fulfilmentCenter);
    }
}
