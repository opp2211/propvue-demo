package ru.maltsev.propvuedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maltsev.propvuedemo.model.Product;
import ru.maltsev.propvuedemo.model.ProductStatus;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {

    List<Product> findAllByStatus(ProductStatus status);
}
