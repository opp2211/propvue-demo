package ru.maltsev.propvuedemo.controller;

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

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto addNew(@Validated @RequestBody ProductRequestDto productDto) {
        return productService.addNew(productDto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable @Positive long id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam(required = false) ProductStatus status) { //todo check null and invalid
        return productService.getAll(status);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable @Positive Long id,
                                     @RequestBody @Validated ProductRequestDto productDto) {
        return productService.update(id, productDto);
    }

    @DeleteMapping("/{id}")
    public ProductResponseDto remove(@PathVariable @Positive Long id) {
        return productService.remove(id);
    }

    @GetMapping("/total-value") //todo MethodArgumentTypeMismatchException if invalid status
    public BigDecimal getTotalValueOfProducts(@RequestParam(required = false) ProductStatus status,
                                              @RequestParam(required = false) String fulfilmentCenter) {
        return productService.getTotalValueOfProducts(status, fulfilmentCenter);
    }
}
