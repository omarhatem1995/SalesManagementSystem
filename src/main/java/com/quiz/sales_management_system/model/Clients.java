package com.quiz.sales_management_system.model;

import com.quiz.sales_management_system.dto.clients.AddClientsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Clients {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "mail")
    private String mail;
    @Column(name = "address")
    private String address;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Clients(AddClientsDTO addClientDTO) {
        this.firstName = addClientDTO.getFirstName();
        this.lastName = addClientDTO.getLastName();
        this.mobile = addClientDTO.getMobile();
        this.address = addClientDTO.getAddress();
        this.mail = addClientDTO.getEmail();
    }
}
