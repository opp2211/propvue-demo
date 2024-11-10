package ru.maltsev.propvuedemo.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maltsev.propvuedemo.dto.ProductRequestDto;
import ru.maltsev.propvuedemo.dto.ProductResponseDto;
import ru.maltsev.propvuedemo.exception.NotFoundException;
import ru.maltsev.propvuedemo.mapper.ProductMapper;
import ru.maltsev.propvuedemo.model.Product;
import ru.maltsev.propvuedemo.model.ProductStatus;
import ru.maltsev.propvuedemo.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Transactional
    public ProductResponseDto addNew(ProductRequestDto productDto) {
        Product newProduct = mapper.toEntity(productDto);
        productRepository.save(newProduct);
        log.info("Product was saved to DB: {}", newProduct);
        return mapper.toResponseDto(newProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getById(long id) {
        Product product = getProduct(id);
        return mapper.toResponseDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAll(@Nullable ProductStatus status) {
        List<Product> products;
        if (status == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findAllByStatus(status);
        }
        return products.stream().map(mapper::toResponseDto).toList();
    }

    @Transactional
    public ProductResponseDto update(long id, ProductRequestDto productRequestDto) {
        checkExistence(id);

        Product updatedProduct = mapper.toEntity(productRequestDto, id);
        productRepository.save(updatedProduct);
        log.info("Product was updated: {}", updatedProduct);
        return mapper.toResponseDto(updatedProduct);
    }

    @Transactional
    public ProductResponseDto remove(long id) {
        Product deletingProduct = getProduct(id);
        productRepository.delete(deletingProduct);
        return mapper.toResponseDto(deletingProduct);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalValueOfProducts(@Nullable ProductStatus status, @Nullable String fulfilmentCenter) {
        return productRepository.getTotalValueByStatusAndFfmtCenter(status, fulfilmentCenter);
    }


    private Product getProduct(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Product id=%d not found!", id)));
    }

    private void checkExistence(long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Product id=%d not found!", id));
        }
    }
}
