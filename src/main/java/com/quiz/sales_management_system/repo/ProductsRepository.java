package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

    @Query("SELECT p FROM Products p")
    Page<Products> findAllProducts(Pageable pageable);
}