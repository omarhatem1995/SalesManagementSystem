package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

    @Query("SELECT p FROM Sales p")
    Page<Sales> findAllSales(Pageable pageable);
}