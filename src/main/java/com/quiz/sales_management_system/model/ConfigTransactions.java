package com.quiz.sales_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "config_transactions", schema = "sales_management_system", catalog = "")
public class ConfigTransactions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "transaction_code")
    private Integer transactionCode;
    @Column(name = "description")
    private String description;
}
