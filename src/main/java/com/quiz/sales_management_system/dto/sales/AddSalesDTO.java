package com.quiz.sales_management_system.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSalesDTO {
    int clientId;
    int sellerId;
    List<ProductSalesDTO> productsList;

}
