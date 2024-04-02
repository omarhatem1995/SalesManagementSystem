package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Integer> {

    @Query("SELECT sd.productId, SUM(sd.quantity) AS totalQuantity " +
            "FROM SaleDetails sd " +
            "INNER JOIN Sales s ON sd.saleId = s.id " +
            "WHERE s.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY sd.productId " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts(Date startDate, Date endDate);


        @Query("SELECT sd.productId, SUM(sd.quantity) AS totalQuantity " +
                "FROM SaleDetails sd " +
                "GROUP BY sd.productId " +
                "ORDER BY totalQuantity DESC")
        List<Object[]> findTotalQuantitySoldPerProduct();

        @Query("SELECT sd.productId, SUM(sd.quantity) AS totalQuantity " +
                "FROM SaleDetails sd " +
                "GROUP BY sd.productId " +
                "ORDER BY totalQuantity ASC")
        List<Object[]> findLeastSoldProducts();
    List<SaleDetails> findBySaleId(Integer integer);
}