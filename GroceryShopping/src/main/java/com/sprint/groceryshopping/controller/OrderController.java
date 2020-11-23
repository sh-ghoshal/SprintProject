package com.sprint.groceryshopping.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.groceryshopping.dto.OrderDTO;
import com.sprint.groceryshopping.exception.DatabaseException;
import com.sprint.groceryshopping.exception.OrderNotFoundException;
import com.sprint.groceryshopping.service.IOrderService;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private IOrderService orderService;
	
	
	@PostMapping("/orders")
	public ResponseEntity<List<OrderDTO>> insertOrder(@Valid @RequestBody OrderDTO order) throws OrderNotFoundException {
		
		System.out.println(order);
		
		log.info("Insert Order Start");
		List<OrderDTO> orders= orderService.createOrder(order);
		
		log.info("Order Inserted");
		return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.OK);
	}
	
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDTO>> getAllOrders() throws DatabaseException{
		
		
		log.info("Fetching all orders ");
		List<OrderDTO> orders = orderService.getAllOrders();
		
		log.info("all available orders fetched");
		return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.OK);
	}
	
	
	@GetMapping("/orders/user/{emailId}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersByUser(@PathVariable("emailId") String emailId) throws OrderNotFoundException,DatabaseException{
		
		log.info("Finding orders by user !");
		List<OrderDTO> orders = orderService.findOrdersByUser(emailId);
		
		
		log.info("Orders received");
		return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.OK);
	}
	

	@DeleteMapping("orders/{orderId}")
	public ResponseEntity<String> deleteOrder(
			@PathVariable("orderId")int orderId) throws  OrderNotFoundException{
		
		String status= orderService.deleteOrder(orderId);
			
		return new ResponseEntity<String>(status, HttpStatus.OK);
		
	}
	
	
	@GetMapping("orders/{orderId}")
	public ResponseEntity<OrderDTO> findOrder (
			@PathVariable("orderId")Integer orderId) throws OrderNotFoundException{
		
		log.info("Finding Order");
		OrderDTO order= orderService.findOrder(orderId);
		
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
	}
	
	
	@PutMapping("/orders")
	public ResponseEntity<List<OrderDTO>> updateOrder (
			@Valid @RequestBody OrderDTO order)throws OrderNotFoundException, DatabaseException{
		
		log.info("Updating Order");
		List<OrderDTO> orders = orderService.updateOrder(order);
		
		log.info("Order Updated");
		return new ResponseEntity<List<OrderDTO>>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/orders/{date}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersByDate(@PathVariable("date") LocalDate date){
		
		log.info("Finding orders by user !");
		List<OrderDTO> orders = orderService.findOrdersByDate(date);
		
		log.info("Orders for the given date found !");
		return new ResponseEntity<List<OrderDTO>>(orders,HttpStatus.OK);
	}

}