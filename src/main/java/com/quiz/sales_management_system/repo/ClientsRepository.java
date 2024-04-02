package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.dto.clients.ClientActivity;
import com.quiz.sales_management_system.model.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientsRepository extends JpaRepository<Clients, Integer> {

    @Query("SELECT p FROM Clients p")
    Page<Clients> findAllClients(Pageable pageable);

    @Query("SELECT c FROM Clients c INNER JOIN Sales s ON s.clientId = c.id GROUP BY c.id ORDER BY SUM(s.total) DESC")
    List<Clients> findTopSpendingClients();

    boolean existsByMail(String email);

}