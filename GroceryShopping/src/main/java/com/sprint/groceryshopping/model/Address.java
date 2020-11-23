 package com.sprint.groceryshopping.model;

 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;


 @Entity
 @Table(name="address_details")
 public class Address {
 	private static final Logger log = LoggerFactory.getLogger(Address.class);
 	private static final long serialVersionUID = 1L;
 	@Id
 	@Column(name="address_id")
 	@GeneratedValue(strategy=GenerationType.AUTO)
 	private int addressId;
 	
 	@Column(name="country")
 	private String country;
 	
 	@Column(name="state")
 	private String state;
 	
 	@Column(name="district")
 	private String district;
 	
 	@Column(name="pincode")
 	private String pinCode;
 	
 	public Address() {
 		
 	}
 	
 	public Address(String country, String state, String district, String pinCode) {
 	
 		this.country = country;
 		this.state = state;
 		this.district = district;
 		this.pinCode = pinCode;
 	}
 	
 	
 	public int getAddressId() {
 		return addressId;
 	}
 	public void setAddressId(int addressId) {
 		this.addressId = addressId;
 	}
 	
 	public String getCountry() {
 		return country;
 	}
 	public void setCountry(String country) {
 		this.country = country;
 	}
 	public String getState() {
 		return state;
 	}
 	public void setState(String city) {
 		this.state = city;
 	}
 	public String getDistrict() {
 		return district;
 	}
 	public void setDistrict(String district) {
 		this.district = district;
 	}
 	public String getPinCode() {
 		return pinCode;
 	}
 	public void setPinCode(String pinCode) {
 		this.pinCode= pinCode;
 	}
 	
 	public String toString() {
 		return "Address [addressId=" + addressId + ", country=" + country + ", state=" + state
 				+ ", district=" + district + "Pincode=" + pinCode +"]";
 	}
 	
 	}
