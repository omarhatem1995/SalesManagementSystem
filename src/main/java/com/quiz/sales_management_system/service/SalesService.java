package com.quiz.sales_management_system.service;

import com.quiz.sales_management_system.dto.sales.AddSalesDTO;
import com.quiz.sales_management_system.dto.sales.ProductSalesDTO;
import com.quiz.sales_management_system.dto.sales.UpdateSalesDTO;
import com.quiz.sales_management_system.model.*;
import com.quiz.sales_management_system.model.Sales;
import com.quiz.sales_management_system.model.Sales;
import com.quiz.sales_management_system.repo.*;
import com.quiz.sales_management_system.utils.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SalesService {

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    SaleDetailsRepository saleDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private ProductsRepository productsRepository;

    public ResponseEntity<?> findAllSales(){
        ReturnObject returnObject = new ReturnObject();
        try {
            List<Sales> salesList = salesRepository.findAll();
            if (salesList.isEmpty()) {
                returnObject.setData(salesList);
                returnObject.setMessage("No Sales Found");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setData(salesList);
                returnObject.setMessage("Loaded Sales Successfully");
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

    public ResponseEntity findAllSalesPaging(int page, int size) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Page<Sales> salesPage = salesRepository.findAllSales(PageRequest.of(page, size));
            if (salesPage.isEmpty()) {
                returnObject.setData(salesPage.getContent());
                returnObject.setMessage("No Sales Found");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setData(salesPage.getContent());
                returnObject.setMessage("Loaded Sales Successfully");
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

    public ResponseEntity<?> addSales(AddSalesDTO addSalesDTO){
        Optional<User> userOptional = userRepository.findById(addSalesDTO.getSellerId());
        Optional<Clients> clientsOptional = clientsRepository.findById(addSalesDTO.getClientId());
        ReturnObject returnObject = new ReturnObject();
        if(userOptional.isPresent()){

            if(!clientsOptional.isPresent()){
                returnObject.setStatus(false);
                returnObject.setData(null);
                returnObject.setMessage("Client Id Not Found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
            }

            Sales sale = new Sales(addSalesDTO);
            boolean saleSaved = false;
            Double salesTotalPrice = 0.0;

            for (ProductSalesDTO productSaleDTO : addSalesDTO.getProductsList()) {
                // Find the product by ID
                Optional<Products> productOptional = productsRepository.findById(productSaleDTO.getProductId());
                if (productOptional.isPresent()) {
                    Products product = productOptional.get();
                    // Check if the product has enough quantity
                    if (product.getAvailableQuantity() < productSaleDTO.getQuantity()) {
                        returnObject.setStatus(false);
                        returnObject.setData(null);
                        returnObject.setMessage("Product With Id " + productSaleDTO.getProductId() + " doesn't have enough quantity, Maximum quantity is: " + product.getAvailableQuantity());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                    }

                    if (!saleSaved) {
                        sale = salesRepository.save(sale);
                        saleSaved = true; // Set the flag to true after saving the sale
                    }

                    SaleDetails saleDetails = new SaleDetails();
                    saleDetails.setQuantity(productSaleDTO.getQuantity());
                    saleDetails.setProductId(product.getId());
                    saleDetails.setSaleId(sale.getId()); // Set the saleId

                    // Add SaleDetails to the Sale entity
                    salesTotalPrice += (product.getPrice() * productSaleDTO.getQuantity());
                    saleDetailsRepository.save(saleDetails);

                    int newQuantity = product.getAvailableQuantity() - productSaleDTO.getQuantity();
                    product.setAvailableQuantity(newQuantity);
                    productsRepository.save(product);
                }else{
                    returnObject.setStatus(false);
                    returnObject.setData(null);
                    returnObject.setMessage("Product With Id "+ productSaleDTO.getProductId() + "Not Found");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
            }

            sale.setTotal(salesTotalPrice);
            Timestamp createdAtTimestamp = Timestamp.valueOf(LocalDateTime.now());
            sale.setCreatedAt(createdAtTimestamp);
            salesRepository.save(sale);
            returnObject.setData(addSalesDTO);
            returnObject.setMessage("Sale created successfully with Total Price: " + salesTotalPrice);
            returnObject.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(returnObject);

        }else{
            returnObject.setStatus(false);
            returnObject.setData(null);
            returnObject.setMessage("Seller Id Not Found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
        }

    }

    public ResponseEntity<?> updateSales(Integer id , UpdateSalesDTO updateSalesDTO){
        ReturnObject returnObject = new ReturnObject();

            Optional<Sales> salesOptional = salesRepository.findById(id);
            if(!salesOptional.isPresent()){
                returnObject.setStatus(false);
                returnObject.setMessage("Sales Id Not Found");
                returnObject.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
            }
            Sales sale = salesOptional.get();
            Double salesTotalPrice = 0.0;
            List<SaleDetails> saleDetailsList = saleDetailsRepository.findBySaleId(id);

            for (ProductSalesDTO productSaleDTO : updateSalesDTO.getListOfUpdatedProducts()) {
                Optional<Products> productOptional = productsRepository.findById(productSaleDTO.getProductId());
                if (productOptional.isPresent()) {
                    Products product = productOptional.get();
                    int oldQuantity = 0;

                    Optional<SaleDetails> saleDetailOptional = saleDetailsList.stream()
                            .filter(sd -> sd.getProductId() == product.getId())
                            .findFirst();

                    if (!saleDetailOptional.isPresent()) {
                        returnObject.setStatus(false);
                        returnObject.setData(null);
                        returnObject.setMessage("Product With Id " + product.getId() + " was not part of the original sale");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                    }

                    if (saleDetailOptional.isPresent()) {
                        oldQuantity = saleDetailOptional.get().getQuantity();
                    }

                    int updatedQuantity = productSaleDTO.getQuantity();
                    if (updatedQuantity > oldQuantity) {
                        int increaseQuantity = updatedQuantity - oldQuantity;
                        int newQuantity = product.getAvailableQuantity() + increaseQuantity;
                        product.setAvailableQuantity(newQuantity);
                    } else if (updatedQuantity < oldQuantity) {
                        int decreaseQuantity = oldQuantity - updatedQuantity;
                        int newQuantity = product.getAvailableQuantity() - decreaseQuantity;
                        if (newQuantity < 0) {
                            returnObject.setStatus(false);
                            returnObject.setData(null);
                            returnObject.setMessage("Product With Id " + product.getId() + " doesn't have enough quantity");
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                        }
                        product.setAvailableQuantity(newQuantity);
                    }

                    productsRepository.save(product);

                    SaleDetails saleDetails = saleDetailOptional.orElse(new SaleDetails());
                    saleDetails.setQuantity(updatedQuantity);
                    salesTotalPrice += (product.getPrice() * updatedQuantity);
                    saleDetailsRepository.save(saleDetails);
                } else {
                    returnObject.setStatus(false);
                    returnObject.setData(null);
                    returnObject.setMessage("Product With Id " + productSaleDTO.getProductId() + "Not Found");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
            }

            sale.setTotal(salesTotalPrice);
            Timestamp createdAtTimestamp = Timestamp.valueOf(LocalDateTime.now());
            sale.setCreatedAt(createdAtTimestamp);
            salesRepository.save(sale);
            returnObject.setData(updateSalesDTO);
            returnObject.setMessage("Sale Updated successfully with Total Price: " + salesTotalPrice);
            returnObject.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(returnObject);


    }
}
