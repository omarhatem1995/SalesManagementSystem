package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, Integer> {
}