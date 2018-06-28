package br.com.alura.store.dto;

import java.util.ArrayList;
import java.util.Collection;

public class CartPostDTO {

    private String street;
    private String city;
    private Collection<ProductDTO> products = new ArrayList<>();

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductDTO> products) {
        this.products = products;
    }
}