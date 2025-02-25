package com.restaurant.order_service.dto;

public class ClientResponse {
     // esta classe evita dependencia direta do modelo do serviço externo
    private int id;
    private String name;
    private String email;

    

    public ClientResponse() {
    }

    
    public ClientResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
}
