package com.quiz.sales_management_system.controller;

import com.quiz.sales_management_system.dto.products.AddProductDTO;
import com.quiz.sales_management_system.dto.products.UpdateProductDTO;
import com.quiz.sales_management_system.dto.sales.AddSalesDTO;
import com.quiz.sales_management_system.dto.sales.UpdateSalesDTO;
import com.quiz.sales_management_system.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sales")
public class SalesController {

    @Autowired
    SalesService salesService;

    @GetMapping("")
    public ResponseEntity<?> findAllSales() {
        return salesService.findAllSales();
    }

    @GetMapping("paging")
    public ResponseEntity<?> findAllSalesPaging(int page,int size) {
        return salesService.findAllSalesPaging(page,size);
    }

    @PostMapping("")
    public ResponseEntity<?> addSales(@RequestBody AddSalesDTO addSalesDTO) {
        return salesService.addSales(addSalesDTO);
    }

    @PutMapping("{salesId}")
    public ResponseEntity<?> updateSales(@PathVariable Integer salesId ,@RequestBody UpdateSalesDTO updateSalesDTO) {
        return salesService.updateSales(salesId,updateSalesDTO);
    }


}
