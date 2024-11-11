package ru.maltsev.propvuedemo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maltsev.propvuedemo.dto.ProductRequestDto;
import ru.maltsev.propvuedemo.dto.ProductResponseDto;
import ru.maltsev.propvuedemo.exception.NotFoundException;
import ru.maltsev.propvuedemo.mapper.ProductMapperImpl;
import ru.maltsev.propvuedemo.model.Product;
import ru.maltsev.propvuedemo.model.ProductStatus;
import ru.maltsev.propvuedemo.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapperImpl productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void testAddNew() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .status(ProductStatus.SELLABLE)
                .fulfilmentCenter("1")
                .quantity(1)
                .value(BigDecimal.valueOf(10.10))
                .build();

        when(productRepository.save(any(Product.class))).thenAnswer(invocationOnMock -> {
            Product product = invocationOnMock.getArgument(0, Product.class);
            product.setProductId(111L);
            return product;
        });

        ProductResponseDto result = productService.addNew(productRequestDto);

        assertNotNull(result.getProductId());
        assertEquals(productRequestDto.getStatus(), result.getStatus());
        assertEquals(productRequestDto.getFulfilmentCenter(), result.getFulfilmentCenter());
        assertEquals(productRequestDto.getQuantity(), result.getQuantity());
        assertEquals(productRequestDto.getValue(), result.getValue());
        verify(productMapper, times(1)).toEntity(productRequestDto);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toResponseDto(any(Product.class));
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testGetById() {
        long productId = 1L;
        Product product = Product.builder()
                .productId(productId)
                .status(ProductStatus.SELLABLE)
                .fulfilmentCenter("1")
                .quantity(1)
                .value(BigDecimal.valueOf(10.10))
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.getById(productId);

        assertEquals(productId, result.getProductId());
        assertEquals(product.getStatus(), result.getStatus());
        assertEquals(product.getFulfilmentCenter(), result.getFulfilmentCenter());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getValue(), result.getValue());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toResponseDto(any(Product.class));
        verifyNoMoreInteractions(productMapper, productRepository);

    }

    @Test
    void testGetById_notExist_throws() {
        long productId = 999L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getById(productId));
        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testGetAll_statusNull_findAll() {
        ProductStatus status = null;
        Product product1 = Product.builder()
                .productId(1L)
                .status(ProductStatus.SELLABLE)
                .build();
        Product product2 = Product.builder()
                .productId(2L)
                .status(ProductStatus.UNFULFILLABLE)
                .build();
        List<Product> products = List.of(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDto> result = productService.getAll(status);

        assertEquals(products.size(), result.size());
        assertEquals(products.get(0).getProductId(), result.get(0).getProductId());
        assertEquals(products.get(1).getProductId(), result.get(1).getProductId());
        assertEquals(products.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(products.get(1).getStatus(), result.get(1).getStatus());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(2)).toResponseDto(any());
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testGetAll_statusNotNull_findAllByStatus() {
        ProductStatus status = ProductStatus.SELLABLE;
        Product product1 = Product.builder()
                .productId(1L)
                .status(status)
                .build();
        Product product2 = Product.builder()
                .productId(2L)
                .status(status)
                .build();
        List<Product> products = List.of(product1, product2);

        when(productRepository.findAllByStatus(status)).thenReturn(products);

        List<ProductResponseDto> result = productService.getAll(status);

        assertEquals(products.size(), result.size());
        assertEquals(products.get(0).getProductId(), result.get(0).getProductId());
        assertEquals(products.get(1).getProductId(), result.get(1).getProductId());
        assertEquals(products.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(products.get(1).getStatus(), result.get(1).getStatus());
        verify(productRepository, times(1)).findAllByStatus(status);
        verify(productMapper, times(2)).toResponseDto(any());
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testUpdate_found_success() {
        long productId = 1L;
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .status(ProductStatus.SELLABLE)
                .fulfilmentCenter("1")
                .quantity(1)
                .value(BigDecimal.valueOf(10.10))
                .build();

        when(productRepository.existsById(productId)).thenReturn(true);

        ProductResponseDto result = productService.update(productId, productRequestDto);

        assertEquals(productId, result.getProductId());
        assertEquals(productRequestDto.getStatus(), result.getStatus());
        assertEquals(productRequestDto.getFulfilmentCenter(), result.getFulfilmentCenter());
        assertEquals(productRequestDto.getQuantity(), result.getQuantity());
        assertEquals(productRequestDto.getValue(), result.getValue());
        verify(productRepository, times(1)).existsById(productId);
        verify(productMapper, times(1)).toEntity(productRequestDto, productId);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toResponseDto(any(Product.class));
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testUpdate_notFound_throws() {
        long productId = 1L;
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .status(ProductStatus.SELLABLE)
                .fulfilmentCenter("1")
                .quantity(1)
                .value(BigDecimal.valueOf(10.10))
                .build();

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> productService.update(productId, productRequestDto));

        verify(productRepository, times(1)).existsById(productId);
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testRemove_found_success() {
        long productId = 1L;
        Product product = Product.builder()
                .productId(productId)
                .status(ProductStatus.SELLABLE)
                .fulfilmentCenter("1")
                .quantity(1)
                .value(BigDecimal.valueOf(10.10))
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.remove(productId);

        assertEquals(productId, result.getProductId());
        assertEquals(product.getStatus(), result.getStatus());
        assertEquals(product.getFulfilmentCenter(), result.getFulfilmentCenter());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getValue(), result.getValue());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
        verify(productMapper, times(1)).toResponseDto(any(Product.class));
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testRemove_notFound_throws() {
        long productId = 999L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.remove(productId));
        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void testGetTotalValueOfProducts() {
        BigDecimal expected = BigDecimal.ZERO;
        when(productRepository.getTotalValueByStatusAndFfmtCenter(any(), any())).thenReturn(expected);

        BigDecimal result = productService.getTotalValueOfProducts(null, null);

        assertEquals(expected, result);
        verify(productRepository, times(1)).getTotalValueByStatusAndFfmtCenter(any(), any());
        verifyNoMoreInteractions(productMapper, productRepository);
    }

}