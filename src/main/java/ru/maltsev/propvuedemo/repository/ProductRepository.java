package ru.maltsev.propvuedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maltsev.propvuedemo.model.Product;
import ru.maltsev.propvuedemo.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {

    List<Product> findAllByStatus(ProductStatus status);

    @Query(nativeQuery = true, value = """
        SELECT SUM(p.value * p.quantity)
        FROM product p
        WHERE p.status = :stringStatus
    """)
    BigDecimal getTotalValueByStatus(String stringStatus);

    @Query(nativeQuery = true, value = """
        SELECT SUM(p.value * p.quantity)
        FROM product p
    """)
    BigDecimal getTotalValueOfAll();
}
