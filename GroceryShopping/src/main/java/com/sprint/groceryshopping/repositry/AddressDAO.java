package com.sprint.groceryshopping.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.groceryshopping.model.Address;
import com.sprint.groceryshopping.model.Product;

public interface AddressDAO extends JpaRepository<Address, Integer> {

}