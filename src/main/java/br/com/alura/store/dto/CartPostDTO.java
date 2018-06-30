package br.com.alura.store.dto;

import br.com.alura.store.model.Product;

import java.util.ArrayList;
import java.util.Collection;

public class CartPostDTO {

    private String street;
    private String city;
    private Collection<Product> products = new ArrayList<>();

    public CartPostDTO() {

    }

    public CartPostDTO(String street, String city, Collection<Product> products) {
        this.street = street;
        this.city = city;
        this.products = products;
    }

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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}