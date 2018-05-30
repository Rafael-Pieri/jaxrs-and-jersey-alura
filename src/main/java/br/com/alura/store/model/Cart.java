package br.com.alura.store.model;

import br.com.alura.store.dto.CartDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.beans.BeanUtils;

@Entity
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    private String street;

    private String city;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Product> products = new ArrayList<>();

    public Cart() {}

    public Cart(CartDTO cartDTO) {
        BeanUtils.copyProperties(cartDTO, this);
        this.street = cartDTO.getStreet();
        this.city = cartDTO.getCity();
        this.products = cartDTO.getProducts().stream()
            .map(Product::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public Long getId() {
        return id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<Product> getProducts() {
        return products;
    }
}