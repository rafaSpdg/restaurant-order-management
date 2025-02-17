package com.restaurant.client_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.client_service.model.Client;
import com.restaurant.client_service.service.ClientService;


@SpringBootTest
public class ClientServiceTests {
    
    @Autowired
    private ClientService clientService;

    @Test
    public void testSaveClient() {
        Client client = new Client();
        client.setName("Rafa");
        client.setEmail("rafa@example.com");
        client.setPhone("+271432-43");
        client.setActive(true);

        Client savedClient = clientService.saveClient(client);

        assertNotNull(savedClient);
        assertEquals("Rafa", savedClient.getName());
    }
}
