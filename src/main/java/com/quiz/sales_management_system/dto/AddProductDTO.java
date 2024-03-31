package com.quiz.sales_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDTO {

    String name;
    String description;
    String category;
    Integer quantity;
    Double price;
}
