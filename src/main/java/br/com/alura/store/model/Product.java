package br.com.alura.store.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

	private Long id;
	private BigDecimal price;
	private String name;
	private Integer quantity;

	public Product(Long id, String name, BigDecimal price, Integer quantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Product() {
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
