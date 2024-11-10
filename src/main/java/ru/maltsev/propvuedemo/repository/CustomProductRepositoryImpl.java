package ru.maltsev.propvuedemo.repository;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.maltsev.propvuedemo.model.Product;
import ru.maltsev.propvuedemo.model.ProductStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager em;
    @Override
    public BigDecimal getTotalValueByStatusAndFfmtCenter(@Nullable ProductStatus status, @Nullable String ffmtCenter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        if (status != null) {
            predicates.add(criteriaBuilder.equal(productRoot.get("status"), status.toString()));
        }
        if (ffmtCenter != null) {
            predicates.add(criteriaBuilder.equal(productRoot.get("fulfilmentCenter"), ffmtCenter));
        }

        criteriaQuery.select(
                criteriaBuilder.sum(
                        criteriaBuilder.prod(
                                productRoot.get("quantity"), productRoot.get("value"))));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
