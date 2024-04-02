package com.quiz.sales_management_system.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDTO {
    private Date startDate;
    private Date endDate;
    private int totalSales;
    private double totalRevenue;
    private List<TopSellingProduct> topSellingProducts;
    private List<TopPerformingSeller> topPerformingSellers;
}