package br.com.alura.store.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CART")
public class Cart {

    @GeneratedValue
    @Id
    private Long id;
    private String street;
    private String city;
    @OneToMany
    private List<Product> products = new ArrayList<>();

    public Cart() {
    }

//    public Cart add(Product product) {
//        products.add(product);
//        return this;
//    }
//
//    public Product findProductById(Long productId) {
//        return products.stream().filter(c -> c.getId() == productId).findFirst().get();
//    }
//
//    public Cart to(String street, String city) {
//        this.street = street;
//        this.city = city;
//        return this;
//    }

//    public void remove(Long id) {
//        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext(); ) {
//            Product product = iterator.next();
//
//            if (product.getId() == id) {
//                iterator.remove();
//            }
//        }
//    }
//
//    public void change(Product product) {
//        remove(product.getId());
//        add(product);
//    }
//
//    public void changeQuantity(Product product) {
//        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext(); ) {
//            Product actualProduct = iterator.next();
//
//            if (actualProduct.getId() == product.getId()) {
//                actualProduct.setQuantity(product.getQuantity());
//                return;
//            }
//        }
//    }

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
