package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
}