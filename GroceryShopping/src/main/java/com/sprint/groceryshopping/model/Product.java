package com.sprint.groceryshopping.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="product_details")
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "code")
	private long code;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "price")
	private String price;
	
	@Column(name="description")
	private String description;
	
	@Column(name="brand")
	private String brand;
	
	public Product() {
		
	}
	 
	public Product(String name, String price, String description, String brand) {
			
		
		//this.code = code;
		this.name = name;
		this.price = price;
		this.description = description;
		this.brand = brand;
	}


	public long getCode() {
		return code;
	}


	public void setCode(long code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", brand=" + brand + "]";
	}
	
}