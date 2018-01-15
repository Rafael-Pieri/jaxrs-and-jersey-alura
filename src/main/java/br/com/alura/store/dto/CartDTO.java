package br.com.alura.store.dto;

import br.com.alura.store.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private Long id;
    private String street;
    private String city;
    private List<ProductDTO> products = new ArrayList<>();

    public CartDTO() {
    }

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

    public List<ProductDTO> getProducts() {
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

    public CartDTO withProducts(List<ProductDTO> products) {
        this.products = products;
        return this;
    }

}
