package com.quiz.sales_management_system.service;

import com.quiz.sales_management_system.dto.clients.AddClientsDTO;
import com.quiz.sales_management_system.dto.clients.UpdateClientsDTO;
import com.quiz.sales_management_system.model.Clients;
import com.quiz.sales_management_system.repo.ClientsRepository;
import com.quiz.sales_management_system.utils.ReturnObject;
import com.quiz.sales_management_system.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired
    ClientsRepository clientsRepository;

    public ResponseEntity<?> findAllClients(){
        ReturnObject returnObject = new ReturnObject();
        try {
            List<Clients> clientsList = clientsRepository.findAll();
            if (clientsList.isEmpty()) {
                returnObject.setData(clientsList);
                returnObject.setMessage("No Clients Found");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setData(clientsList);
                returnObject.setMessage("Loaded Clients Successfully");
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

    public ResponseEntity findAllClientsPaging(int page, int size) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Page<Clients> clientsPage = clientsRepository.findAllClients(PageRequest.of(page, size));
            if (clientsPage.isEmpty()) {
                returnObject.setData(clientsPage.getContent());
                returnObject.setMessage("No Clients Found");
                returnObject.setStatus(true);
                return ResponseEntity.status(HttpStatus.OK).body(returnObject);
            } else {
                returnObject.setData(clientsPage.getContent());
                returnObject.setMessage("Loaded Clients Successfully");
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

    public ResponseEntity<?> addClient(AddClientsDTO addClientDTO){
        ReturnObject returnObject = new ReturnObject();
        try {
            if (ValidationUtils.isValidEmail(addClientDTO.getEmail())) {
                if (!ValidationUtils.isValidUserName(addClientDTO.getFirstName()) || !ValidationUtils.isValidUserName(addClientDTO.getLastName())) {
                    returnObject.setData(null);
                    returnObject.setMessage("Invalid first name or last name");
                    returnObject.setStatus(false);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
                if (!ValidationUtils.isValidAddress(addClientDTO.getAddress())) {
                    returnObject.setData(null);
                    returnObject.setMessage("Invalid Address");
                    returnObject.setStatus(false);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
                if(!clientsRepository.existsByMail(addClientDTO.getEmail())) {
                    Clients client = new Clients(addClientDTO);
                    clientsRepository.save(client);
                    returnObject.setStatus(true);
                    returnObject.setData(addClientDTO);
                    returnObject.setMessage("Added Client Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(returnObject);
                }else{
                    returnObject.setStatus(false);
                    returnObject.setData(addClientDTO);
                    returnObject.setMessage("Email already exists " + addClientDTO.getEmail());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
            }else{
                returnObject.setStatus(false);
                returnObject.setData(addClientDTO);
                returnObject.setMessage("Invalid email address" + addClientDTO.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
            }
        } catch (Exception exception) {
            returnObject.setData(null);
            returnObject.setMessage("Failed to add Product");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }
    public ResponseEntity<?> updateClient(int id ,UpdateClientsDTO updateClientDTO){
        ReturnObject returnObject = new ReturnObject();
        try {
            if (ValidationUtils.isValidEmail(updateClientDTO.getEmail())) {
                if (!ValidationUtils.isValidUserName(updateClientDTO.getFirstName()) || !ValidationUtils.isValidUserName(updateClientDTO.getLastName())) {
                    returnObject.setData(null);
                    returnObject.setMessage("Invalid first name or last name");
                    returnObject.setStatus(false);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
                if (!ValidationUtils.isValidAddress(updateClientDTO.getAddress())) {
                    returnObject.setData(null);
                    returnObject.setMessage("Invalid Address");
                    returnObject.setStatus(false);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
                if(!clientsRepository.existsByMail(updateClientDTO.getEmail())) {
                    Optional<Clients> clientOptional = clientsRepository.findById(id);
                    if(clientOptional.isPresent()) {
                        Clients client = clientOptional.get();
                        clientsRepository.save(client);
                        returnObject.setStatus(true);
                        returnObject.setData(updateClientDTO);
                        returnObject.setMessage("Updated Client Successfully");
                        return ResponseEntity.status(HttpStatus.OK).body(returnObject);
                    }else{
                        returnObject.setStatus(false);
                        returnObject.setData(updateClientDTO);
                        returnObject.setMessage("Client Id doesn't Exist :" + id);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                    }
                }else{
                    returnObject.setStatus(false);
                    returnObject.setData(updateClientDTO);
                    returnObject.setMessage("Email already exists " + updateClientDTO.getEmail());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
                }
            }else{
                returnObject.setStatus(false);
                returnObject.setData(updateClientDTO);
                returnObject.setMessage("Invalid email address" + updateClientDTO.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnObject);
            }
        } catch (Exception exception) {
            returnObject.setData(null);
            returnObject.setMessage("Failed to add Product");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }

    public ResponseEntity deleteClient(int id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            clientsRepository.deleteById(id);
            returnObject.setMessage("Client deleted successfully");
            returnObject.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(returnObject);
        } catch (Exception exception) {
            returnObject.setMessage("Failed to delete client");
            returnObject.setStatus(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnObject);
        }
    }
}
