package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
    List<Sales> findByCreatedAtBetween(Date startDate, Date endDate);

    @Query("SELECT p FROM Sales p")
    Page<Sales> findAllSales(Pageable pageable);

    @Query("SELECT s.sellerId, SUM(s.total) AS totalRevenue " +
            "FROM Sales s " +
            "WHERE s.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY s.sellerId " +
            "ORDER BY totalRevenue DESC")
    List<Object[]> findTopPerformingSellers(Date startDate, Date endDate);
}