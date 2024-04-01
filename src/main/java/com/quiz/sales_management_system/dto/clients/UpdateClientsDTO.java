package com.quiz.sales_management_system.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientsDTO {
    String firstName;
    String lastName;
    String mobile;
    String email;
    String address;
}
