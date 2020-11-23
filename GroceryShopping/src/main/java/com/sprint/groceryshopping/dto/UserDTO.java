package com.sprint.groceryshopping.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String emailId;
	
	private String mobileNo;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private Set<OrderDTO> orders = new HashSet<>();

	public UserDTO() {
		
	}

	public UserDTO(String emailId, String mobileNo, String firstName, String lastName, String password,
			Set<OrderDTO> orders) {
		
		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.orders = orders;
	}

	
	public UserDTO(String emailId, String mobileNo, String firstName, String lastName, String password) {
		super();
		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderDTO> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "UserDTO [emailId=" + emailId + ", mobileNo=" + mobileNo + ", firstName=" + firstName + ", lastName="
				+ lastName + ", password=" + password + ", orders=" + orders + "]";
	}

}