package br.com.alura.store.model;

import br.com.alura.store.dto.CartDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "CART")
public class Cart {

    @GeneratedValue
    @Id
    private Long id;

    private String street;

    private String city;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    public Cart() {

    }

    public Cart(CartDTO cartDTO) {
        BeanUtils.copyProperties(cartDTO, this);
//        this.street = cartDTO.getStreet();
//        this.city = cartDTO.getCity();
//        this.products = cartDTO.getProducts();
    }

    public Cart setId(Long id) {
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

    public List<Product> getProducts() {
        return products;
    }

}
