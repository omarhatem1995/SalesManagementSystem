package com.quiz.sales_management_system.controller;


import com.quiz.sales_management_system.dto.sales.SalesReportDTO;
import com.quiz.sales_management_system.service.SalesReportService;
import com.quiz.sales_management_system.utils.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("report")
public class ReportsController {

    @Autowired
    private SalesReportService salesReportService;
    @GetMapping("/sales")
    public ResponseEntity<?> generateSalesReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return salesReportService.generateSalesReport(startDate, endDate);
    }

    @GetMapping("/client")
    public ResponseEntity<?> generateClientReport() {
        return salesReportService.generateClientReport();
    }


    @GetMapping("/product")
    public ResponseEntity<?> generateProductReport() {
        return salesReportService.generateProductReport();
    }


}
