package com.sprint.groceryshopping.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class Payment {
	@Entity
	@Table(name="payment_details")
	public class Address implements Serializable {
		
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="payment_id")
		@GeneratedValue(strategy=GenerationType.AUTO)
		private int addressId;
		
		
		public Address() {
			
		}
	}

}
