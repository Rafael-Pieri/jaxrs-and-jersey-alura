package br.com.alura.store.dto;

import java.util.ArrayList;
import java.util.Collection;

public class CartDTO {

    private Long id;
    private String street;
    private String city;
    private Collection<ProductDTO> products = new ArrayList<>();

    public CartDTO() {}

    public CartDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public Long getId() {
        return id;
    }

    public Collection<ProductDTO> getProducts() {
        return products;
    }

    public CartDTO withId(Long id) {
        this.id = id;
        return this;
    }

    public CartDTO withStreet(String street) {
        this.street = street;
        return this;
    }

    public CartDTO withCity(String city) {
        this.city = city;
        return this;
    }

    public CartDTO withProducts(Collection<ProductDTO> products) {
        this.products = products;
        return this;
    }
}