package com.quiz.sales_management_system.dto.clients;

import com.quiz.sales_management_system.model.Clients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReportDTO {
    private long totalNumberOfClients;
    private List<Clients> topSpendingClients;
    private List<ClientActivity> clientActivity;
    private String clientLocationStatistics;

}