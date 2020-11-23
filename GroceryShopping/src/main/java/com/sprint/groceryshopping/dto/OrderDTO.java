package com.sprint.groceryshopping.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderDTO implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private int orderId;
	
	@NotNull(message="Date must not be null")
	private LocalDate date;
	
	@NotNull(message="Amount must not be null")
	private Integer amount;
	
	@NotEmpty(message="Products must not be empty")
	private Set<ProductDTO > products = new HashSet<>();

	private UserDTO user;
	
	private AddressDTO address;
	
	public OrderDTO() {
		
	}

	public OrderDTO(LocalDate date, Set<ProductDTO> products, UserDTO user, AddressDTO address) {
		this.date = date;
		this.products = products;
		this.user = user;
		this.address = address;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Set<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductDTO> products) {
		this.products = products;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDTO other = (OrderDTO) obj;
		if (orderId != other.orderId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", date=" + date + ", amount=" + amount + ", products=" + products
				+ ", user=" + user + ", address=" + address + "]";
	}
	
	
	
}

