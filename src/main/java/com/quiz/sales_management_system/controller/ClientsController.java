package com.quiz.sales_management_system.controller;

import com.quiz.sales_management_system.dto.clients.AddClientsDTO;
import com.quiz.sales_management_system.dto.clients.UpdateClientsDTO;
import com.quiz.sales_management_system.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientsController {

    @Autowired
    ClientsService clientsService;

    @GetMapping("")
    public ResponseEntity<?> findAllClients() {
        return clientsService.findAllClients();
    }


    @GetMapping("paging")
    public ResponseEntity<?> findAllClientsPaging(int page,int size) {
        return clientsService.findAllClientsPaging(page,size);
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody AddClientsDTO addClientsDTO) {
        return clientsService.addClient(addClientsDTO);
    }

    @PutMapping("{clientId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer clientId ,@RequestBody UpdateClientsDTO updateClient) {
        return clientsService.updateClient(clientId,updateClient);
    }

}
