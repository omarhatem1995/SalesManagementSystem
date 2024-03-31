package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
}