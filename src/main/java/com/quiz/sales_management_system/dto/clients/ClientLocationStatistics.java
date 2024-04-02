package com.quiz.sales_management_system.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientLocationStatistics {
    private String location;
    private int numberOfClients;

}
