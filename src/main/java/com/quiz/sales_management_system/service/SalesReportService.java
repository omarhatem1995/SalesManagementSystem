package com.quiz.sales_management_system.service;

import com.quiz.sales_management_system.dto.clients.ClientActivity;
import com.quiz.sales_management_system.dto.clients.ClientReportDTO;
import com.quiz.sales_management_system.dto.products.PricingPerSelling;
import com.quiz.sales_management_system.dto.products.ProductReportDTO;
import com.quiz.sales_management_system.dto.sales.SalesReportDTO;
import com.quiz.sales_management_system.dto.sales.TopPerformingSeller;
import com.quiz.sales_management_system.dto.sales.TopSellingProduct;
import com.quiz.sales_management_system.model.Clients;
import com.quiz.sales_management_system.model.Products;
import com.quiz.sales_management_system.model.Sales;
import com.quiz.sales_management_system.model.User;
import com.quiz.sales_management_system.repo.*;
import com.quiz.sales_management_system.utils.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesReportService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private SaleDetailsRepository saleDetailsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private ClientsRepository clientRepository;

    @Autowired
    private ClientLogsRepository clientLogsRepository;

    public ResponseEntity<?> generateSalesReport(Date startDate, Date endDate) {
        List<Sales> sales = salesRepository.findByCreatedAtBetween(startDate, endDate);

        int totalSales = sales.size();

        double totalRevenue = sales.stream().mapToDouble(Sales::getTotal).sum();

        List<Object[]> topSellingProductsData = saleDetailsRepository.findTopSellingProducts(startDate, endDate);
        List<TopSellingProduct> topSellingProducts = new ArrayList<>();
        for (Object[] data : topSellingProductsData) {
            int productId = (int) data[0];
            long quantitySold = (long) data[1];
            Products product = productsRepository.findById(productId).orElse(null);
            if (product != null) {
                topSellingProducts.add(new TopSellingProduct(product.getName(), quantitySold));
            }
        }

        List<Object[]> topPerformingSellersData = salesRepository.findTopPerformingSellers(startDate, endDate);
        List<TopPerformingSeller> topPerformingSellers = new ArrayList<>();
        for (Object[] data : topPerformingSellersData) {
            int sellerId = (int) data[0];
            double sellerRevenue = (double) data[1];
            User seller = usersRepository.findById(sellerId).orElse(null);
            if (seller != null) {
                topPerformingSellers.add(new TopPerformingSeller(seller.getUsername(), sellerRevenue));
            }
        }

        SalesReportDTO salesReport = new SalesReportDTO();
        salesReport.setStartDate(startDate);
        salesReport.setEndDate(endDate);
        salesReport.setTotalSales(totalSales);
        salesReport.setTotalRevenue(totalRevenue);
        salesReport.setTopSellingProducts(topSellingProducts);
        salesReport.setTopPerformingSellers(topPerformingSellers);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setData(salesReport);
        returnObject.setMessage("Loaded Successfully");
        returnObject.setStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body(returnObject);
    }



    public ResponseEntity<?> generateClientReport() {
        ReturnObject returnObject = new ReturnObject();

        if (clientRepository.count() == 0) {
            returnObject.setData(null);
            returnObject.setMessage("No Clients Yet!");
            returnObject.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(returnObject);
        }
        try {
            ClientReportDTO clientReportDTO = new ClientReportDTO();
            List<Clients> topSpendingClients = clientRepository.findTopSpendingClients();
            clientReportDTO.setTotalNumberOfClients(clientRepository.count());
            clientReportDTO.setTopSpendingClients(topSpendingClients);
            ArrayList<ClientActivity> clientActivities = new ArrayList<>();
            for (int i = 0; i < topSpendingClients.size(); i++) {
                List<ClientActivity> clientActivityList = clientLogsRepository.getClientActivityByClientId(topSpendingClients.get(i).getId());
                clientActivities.addAll(clientActivityList);
            }
            clientReportDTO.setClientActivity(clientActivities);
            clientReportDTO.setClientLocationStatistics(topSpendingClients.get(0).getAddress());

            returnObject.setData(clientReportDTO);
            returnObject.setStatus(true);
            returnObject.setMessage("Loaded Successfully");

            return ResponseEntity.status(HttpStatus.OK).body(returnObject);
        }catch (Exception exception){
            returnObject.setStatus(false);
            returnObject.setMessage("Failed to load Report");
            returnObject.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
        }
    }

    public ResponseEntity<?>  generateProductReport(){
        ProductReportDTO productReportDTO = new ProductReportDTO();
        productReportDTO.setProductsList(productsRepository.findAll());
        productReportDTO.setSalesPerformance(salesRepository.count());
        productReportDTO.setLeastSoldProduct(getLeastSoldProducts());
        productReportDTO.setMostSoldProduct(getMostSoldProducts());

        ReturnObject returnObject = new ReturnObject<>();
        returnObject.setStatus(true);
        returnObject.setData(productReportDTO);
        returnObject.setMessage("Loaded Products Successfully");

        return ResponseEntity.status(HttpStatus.OK).body(returnObject);
    }

    public List<PricingPerSelling> getMostSoldProducts() {
        List<PricingPerSelling> mostSoldProducts = new ArrayList<>();
        List<Object[]> topSellingProductsData = saleDetailsRepository.findTotalQuantitySoldPerProduct();

        for (Object[] data : topSellingProductsData) {
            int productId = (int) data[0];
            long quantitySold = (long) data[1];
            Products product = productsRepository.findById(productId).orElse(null);
            if (product != null) {
                mostSoldProducts.add(new PricingPerSelling(product.getName(),quantitySold,product.getPrice() ));
            }
        }

        return mostSoldProducts;
    }
    public List<PricingPerSelling> getLeastSoldProducts() {
        List<PricingPerSelling> leastSoldProducts = new ArrayList<>();
        List<Object[]> leastSoldProductsData = saleDetailsRepository.findLeastSoldProducts();

        for (Object[] data : leastSoldProductsData) {
            int productId = (int) data[0];
            long quantitySold = (long) data[1];
            Products product = productsRepository.findById(productId).orElse(null);
            if (product != null) {
                leastSoldProducts.add(new PricingPerSelling(product.getName(), quantitySold, product.getPrice()));
            }
        }

        return leastSoldProducts;
    }
}
