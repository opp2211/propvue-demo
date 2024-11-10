package ru.maltsev.propvuedemo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.maltsev.propvuedemo.dto.ProductRequestDto;
import ru.maltsev.propvuedemo.dto.ProductResponseDto;
import ru.maltsev.propvuedemo.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toResponseDto(Product product);
    Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(source = "id", target = "productId")
    Product toEntity(ProductRequestDto productRequestDto, long id);
}
