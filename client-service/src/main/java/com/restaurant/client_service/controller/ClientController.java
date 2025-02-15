package com.restaurant.client_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.client_service.model.Client;
import com.restaurant.client_service.service.ClientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    @Autowired 
    private ClientService clientService;

    @PostMapping("/create-client")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientService.saveClient(client);
        
        return new ResponseEntity<Client>(newClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list-clients")
    public List<Client> getAllClients() {
        return clientService.listClients();
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        boolean isDeleted = clientService.deleteClientbyId(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 no content (recurso deletado)
        } else {
            return ResponseEntity.notFound().build(); // 404 not found (cliente não encontrado)
        }      
    }
}
