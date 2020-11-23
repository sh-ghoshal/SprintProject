package com.sprint.groceryshopping.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.groceryshopping.model.User;

public interface UserDAO extends JpaRepository<User,String> {

}
