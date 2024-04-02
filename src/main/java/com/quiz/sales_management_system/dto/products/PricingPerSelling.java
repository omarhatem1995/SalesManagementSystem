package com.quiz.sales_management_system.dto.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingPerSelling {
    String productName;
    Long quantity;
    Double price;
}
