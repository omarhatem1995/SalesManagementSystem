package com.quiz.sales_management_system.service;

import com.quiz.sales_management_system.dto.AddProductDTO;
import com.quiz.sales_management_system.dto.UpdateProductDTO;
import com.quiz.sales_management_system.model.Products;
import com.quiz.sales_management_system.repo.ProductsRepository;
import com.quiz.sales_management_system.utils.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    public ResponseEntity<?> findAllProducts() {
        ReturnObject returnObject = new ReturnObject();
        try {
            List<Products> productsList = productsRepository.findAll();
            if (productsList.isEmpty()) {
                returnObject.setData(productsList);
                returnObject.setMessage("No Products Found");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setData(productsList);
                returnObject.setMessage("Loaded Products Successfully");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            }
        } catch (Exception exception) {
            returnObject.setData(null);
            returnObject.setMessage("Failed To Load Data");
            returnObject.setStatus(true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }

    public ResponseEntity findAllProductsPaging(int page, int size) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Page<Products> productsPage = productsRepository.findAllProducts(PageRequest.of(page, size));
            if (productsPage.isEmpty()) {
                returnObject.setData(productsPage.getContent());
                returnObject.setMessage("No Products Found");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setData(productsPage.getContent());
                returnObject.setMessage("Loaded Products Successfully");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            }
        } catch (Exception exception) {
            returnObject.setData(null);
            returnObject.setMessage("Failed To Load Data");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }

    public ResponseEntity<?> addProduct(AddProductDTO addProductDTO) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Products product = new Products(addProductDTO);
            productsRepository.save(product);
            returnObject.setStatus(true);
            returnObject.setData(addProductDTO);
            returnObject.setMessage("Added Product Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(returnObject);
        } catch (Exception exception) {
            returnObject.setData(null);
            returnObject.setMessage("Failed to add Product");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }

    public ResponseEntity<?> updateProduct(Integer id, UpdateProductDTO updateProductDTO) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Optional<Products> productOptional = productsRepository.findById(id);
            if (productOptional.isPresent()) {

                Products product = productOptional.get();
                product.setName(updateProductDTO.getName());
                product.setCategory(updateProductDTO.getCategory());
                product.setPrice(updateProductDTO.getPrice());
                product.setAvailableQuantity(updateProductDTO.getQuantity());

                productsRepository.save(product);
                returnObject.setStatus(true);
                returnObject.setData(updateProductDTO);
                returnObject.setMessage("Updated Product Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setStatus(false);
                returnObject.setData(null);
                returnObject.setMessage("Product Not Found");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnObject);
            }
        } catch (Exception exception) {
            returnObject.setData(null);
            returnObject.setMessage("Failed to add Product");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }

    public ResponseEntity deleteProduct(int id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            productsRepository.deleteById(id);
            returnObject.setMessage("Product deleted successfully");
            returnObject.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(returnObject);
        } catch (Exception exception) {
            returnObject.setMessage("Failed to delete product");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }
}
