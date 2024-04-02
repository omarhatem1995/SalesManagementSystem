package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.dto.clients.ClientActivity;
import com.quiz.sales_management_system.model.ClientLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientLogsRepository extends JpaRepository<ClientLogs, Integer> {
    @Query("SELECT new com.quiz.sales_management_system.dto.clients.ClientActivity(cl.clientId, ct.description) " +
            "FROM ClientLogs cl " +
            "JOIN ConfigTransactions ct ON cl.transactionType = ct.transactionCode " +
            "WHERE cl.clientId = :clientId")
    List<ClientActivity> getClientActivityByClientId(@Param("clientId") Integer clientId);

    List<ClientLogs> findByClientId(Integer clientId);
}