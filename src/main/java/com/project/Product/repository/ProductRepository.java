package com.project.Product.repository;

import com.project.Product.model.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductRepository extends JpaRepository<Products,Integer> {
    List<Products> findByBrand(String brand);
    List<Products> findByCart(boolean cart);
}
