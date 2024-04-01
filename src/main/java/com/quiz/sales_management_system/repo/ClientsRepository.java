package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientsRepository extends JpaRepository<Clients, Integer> {

    @Query("SELECT p FROM Clients p")
    Page<Clients> findAllClients(Pageable pageable);

    boolean existsByMail(String email);

}