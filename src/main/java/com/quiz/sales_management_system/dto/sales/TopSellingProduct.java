package com.quiz.sales_management_system.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingProduct {
    private String productName;
    private long quantitySold;
}
