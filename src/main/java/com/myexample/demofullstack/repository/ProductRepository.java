package com.myexample.demofullstack.repository;

import com.myexample.demofullstack.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findAllByOwnerActive(boolean b);
    Optional<List<Product>> findAllByCategoryAndOwnerActive(String c, boolean b);
    Optional<Product> findByIdentifierAndOwnerActive(String id, boolean b);


}
