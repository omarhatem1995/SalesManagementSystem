package com.quiz.sales_management_system.model;

import com.quiz.sales_management_system.dto.AddProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Products {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "available_quantity")
    private Integer availableQuantity;
    @Column(name = "price")
    private Double price;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Products(AddProductDTO addProductDTO) {
        this.name = addProductDTO.getName();
        this.description = addProductDTO.getDescription();
        this.availableQuantity = addProductDTO.getQuantity();
        this.price = addProductDTO.getPrice();
        this.category = addProductDTO.getCategory();
    }
}
