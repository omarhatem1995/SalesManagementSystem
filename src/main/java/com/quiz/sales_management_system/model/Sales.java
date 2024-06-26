package com.quiz.sales_management_system.model;

import com.quiz.sales_management_system.dto.sales.AddSalesDTO;
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
public class Sales {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "seller_id")
    private Integer sellerId;
    @Column(name = "client_id")
    private Integer clientId;
    @Column(name = "total")
    private Double total;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Sales(AddSalesDTO addSalesDTO) {
        this.sellerId = addSalesDTO.getSellerId();
        this.clientId = addSalesDTO.getClientId();
    }

}
