package com.quiz.sales_management_system.dto.products;

import com.quiz.sales_management_system.model.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReportDTO {
    List<Products> productsList;
    Long salesPerformance;
    List<PricingPerSelling> mostSoldProduct;
    List<PricingPerSelling> leastSoldProduct;
}
