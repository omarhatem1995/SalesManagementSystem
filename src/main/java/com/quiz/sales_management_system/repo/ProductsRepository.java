package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}