package br.com.alura.store.model;

import br.com.alura.store.dto.ProductDTO;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.beans.BeanUtils;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    private String name;
    private Integer quantity;

    public Product() {}

    public Product(ProductDTO productDTO) {
        BeanUtils.copyProperties(productDTO, this);
        this.price = productDTO.getPrice();
        this.name = productDTO.getName();
        this.quantity = productDTO.getQuantity();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}