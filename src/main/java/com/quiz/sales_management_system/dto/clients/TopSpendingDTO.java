package com.quiz.sales_management_system.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSpendingDTO {
    Integer clientId;
    String firstName;
    String lastName;
    Double total;
}
