package com.sprint.groceryshopping.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.groceryshopping.model.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {

}