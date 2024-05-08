package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
