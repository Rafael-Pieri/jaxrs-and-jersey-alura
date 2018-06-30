package br.com.alura.store.dto;

public class ProductPutDTO {

    private Integer quantity;

    public ProductPutDTO() {

    }

    public ProductPutDTO(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}