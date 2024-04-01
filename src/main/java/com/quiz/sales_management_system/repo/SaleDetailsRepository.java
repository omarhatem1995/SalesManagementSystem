package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Integer> {


    List<SaleDetails> findBySaleId(Integer integer);
}