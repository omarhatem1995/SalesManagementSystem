package com.quiz.sales_management_system.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientActivity {
    private Integer clientId;
    private String activityDescription;


}
