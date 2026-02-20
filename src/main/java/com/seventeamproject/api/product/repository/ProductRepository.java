package com.seventeamproject.api.product.repository;

import com.seventeamproject.api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
