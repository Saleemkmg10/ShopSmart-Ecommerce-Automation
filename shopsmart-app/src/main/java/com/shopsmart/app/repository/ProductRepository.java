
package com.shopsmart.app.repository;

import com.shopsmart.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Long> {
}