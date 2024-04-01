package com.quiz.sales_management_system.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddClientsDTO {
    String firstName;
    String lastName;
    String mobile;
    String email;
    String address;
}
