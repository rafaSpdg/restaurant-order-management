package com.restaurant.order_service.dto;

public class ClientResponse {
     // esta classe evita dependencia direta do modelo do serviço externo
    private String id;
    private String name;
    private String email;

    

    public ClientResponse() {
    }

    
    public ClientResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
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
