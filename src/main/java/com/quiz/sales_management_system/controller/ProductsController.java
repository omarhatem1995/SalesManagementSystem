package com.quiz.sales_management_system.controller;

import com.quiz.sales_management_system.dto.AddProductDTO;
import com.quiz.sales_management_system.dto.UpdateProductDTO;
import com.quiz.sales_management_system.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping("")
    public ResponseEntity<?> findAllProducts() {
        return productsService.findAllProducts();
    }

    @GetMapping("paging")
    public ResponseEntity<?> findAllProductsPaging(int page,int size) {
        return productsService.findAllProductsPaging(page,size);
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody AddProductDTO addProductDTO) {
        return productsService.addProduct(addProductDTO);
    }

    @PutMapping("{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId ,@RequestBody UpdateProductDTO updateProductDTO) {
        return productsService.updateProduct(productId,updateProductDTO);
    }

}
