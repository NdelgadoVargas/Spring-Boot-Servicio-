package com.pruebatecnica.dto;

public class PhoneDTO {
    private String number;

    // Constructor por defecto
    public PhoneDTO() {}

    // Constructor con parámetros
    public PhoneDTO(String number) {
        this.number = number;
    }

    // Getters y Setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}