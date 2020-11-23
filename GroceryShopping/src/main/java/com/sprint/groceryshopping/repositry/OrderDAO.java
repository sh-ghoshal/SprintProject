package com.sprint.groceryshopping.repositry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sprint.groceryshopping.model.Order;
import com.sprint.groceryshopping.model.User;

@Repository
public interface OrderDAO extends JpaRepository<Order,Integer>
{	
	
	public List<Order> findByUserEmailId(String emailId);
	
	public List<Order> findByDate(LocalDate date);
	
	@Query("SELECT order FROM Order order  WHERE order.date>=:pri AND order.user =: pri")
	public List<Order> getByAmount(@Param("pri") double amount,@Param("pri") User user);

}