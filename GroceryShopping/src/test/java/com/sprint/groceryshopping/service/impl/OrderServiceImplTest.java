package com.sprint.groceryshopping.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.InvalidAttributeValueException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sprint.groceryshopping.dto.AddressDTO;
import com.sprint.groceryshopping.dto.OrderDTO;
import com.sprint.groceryshopping.dto.ProductDTO;
import com.sprint.groceryshopping.dto.UserDTO;
import com.sprint.groceryshopping.exception.DatabaseException;
import com.sprint.groceryshopping.exception.OrderNotFoundException;
import com.sprint.groceryshopping.model.Address;
import com.sprint.groceryshopping.model.Order;
import com.sprint.groceryshopping.model.Product;
import com.sprint.groceryshopping.model.User;
import com.sprint.groceryshopping.repositry.OrderDAO;

@SpringBootTest
public class OrderServiceImplTest {
	
	@Autowired
	private OrderServiceImpl orderService ;
	
	@MockBean
	private OrderDAO orderDao;
	
	
	@Test
	public void getAllOrders() {
		
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order1 = new Order(LocalDate.now(),products,user,address);
		order1.setOrderId(1);
		
		Order order2 = new Order(LocalDate.now(),products,user,address);
		order2.setOrderId(2);
		
		Mockito.when(orderDao.findAll()).thenReturn(Stream.of(order1,order2).collect(Collectors.toList()));
		
		List<OrderDTO> actual = orderService.getAllOrders();
		assertEquals(2,actual.size());
	}

	
	
	@Test
	public void findOrder() {
		
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order = new Order(LocalDate.now(),products,user,address);
		order.setOrderId(1);
		
		
		Mockito.when(orderDao.findById(1)).thenReturn(Optional.of(order));
		
		OrderDTO actual = orderService.findOrder(1);
		assertEquals(order,OrderServiceImpl.DTOToEntity(actual));
		
	}
	
	@Test
	public void findOrderNotPresent() {
		
		
		Mockito.when(orderDao.findById(2)).thenThrow(new OrderNotFoundException());
		
		Exception exception = assertThrows(OrderNotFoundException.class, () -> orderService.findOrder(2));
		assertEquals("Order does not exist.",exception.toString());
		
	}
	
	@Test
	public void findOrderNullIdCheck() {
		
		Integer orderId = null;
		
		OrderDTO actual = orderService.findOrder(orderId);
		assertNull(actual);
	}
	
	@Test
	public void findOrderByDate() {
		
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order = new Order(LocalDate.now(),products,user,address);
		order.setOrderId(1);
		
		
		Mockito.when(orderDao.findByDate(order.getDate())).thenReturn(Stream.of(order).collect(Collectors.toList()));
		
		OrderDTO orderReturned= OrderServiceImpl.entityToDTO(order);
		List<OrderDTO> ordersExpected = Stream.of(orderReturned).collect(Collectors.toList()); //
		
		List<OrderDTO> actual = orderService.findOrdersByDate(order.getDate());
		assertEquals(ordersExpected,actual);	
	}
	
	@Test
	public void findOrderNullDateCheck() {
		
		LocalDate date = null;
		
		List<OrderDTO> actual = orderService.findOrdersByDate(date);
		assertNull(actual);
	}
	
	@Test
	public void findOrderDateInvalid() {

	    LocalDate date = LocalDate.now();
		
		Mockito.when(orderDao.findByDate(date)).thenThrow(new OrderNotFoundException());
		
		 assertThrows(OrderNotFoundException.class, () -> orderService.findOrdersByDate(date));
		
	}
	
	@Test
    public void updateOrder() {
		
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order = new Order(LocalDate.now(),products,user,address);
		order.setOrderId(1);
		
		Mockito.when(orderDao.findById(order.getOrderId())).thenReturn(Optional.of(order));
		
		Mockito.when(orderDao.save(order)).thenReturn(order);
		
		Mockito.when(orderDao.findAll()).thenReturn(Stream.of(order).collect(Collectors.toList()));
		
		List<OrderDTO> actual = orderService.updateOrder(OrderServiceImpl.entityToDTO(order));
		assertEquals(1,actual.size());


	}
	
	@Test
	public void updateOrderCheckNull() {
		

		OrderDTO order = null;
		
		List<OrderDTO> actual = orderService.updateOrder(order);
		assertNull(actual);
	}
	
	@Test
	public void updateOrderInvalid() {     
		
		Set<ProductDTO> products = new HashSet<>();
		UserDTO user = new UserDTO("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		ProductDTO p = new ProductDTO("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		AddressDTO address = new AddressDTO();
		
		OrderDTO order = new OrderDTO(LocalDate.now(),products,user,address);
		order.setOrderId(0);
		
		Mockito.when(orderDao.findById(order.getOrderId())).thenThrow(OrderNotFoundException.class);

		assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(order));	
	}
	
	@Test
    public void createOrder() {
		
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order = new Order(LocalDate.now(),products,user,address);
		order.setOrderId(1);
		
		
		Mockito.when(orderDao.save(order)).thenReturn(order);
		
		Mockito.when(orderDao.findAll()).thenReturn(Stream.of(order).collect(Collectors.toList()));
		
		List<OrderDTO> actual = orderService.createOrder(OrderServiceImpl.entityToDTO(order));
		assertEquals(1,actual.size());
	}
	
	@Test
	public void createOrderCheckNull() {
	
		OrderDTO order = null;
		
		List<OrderDTO> actual = orderService.createOrder(order);
		assertNull(actual);
	}
	
	public void createOrderInvalid() {
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order = new Order(LocalDate.now(),products,user,address);
		Integer id = null;
		order.setOrderId(id);
		
		Mockito.when(orderDao.save(order)).thenThrow(InvalidAttributeValueException.class);
		assertThrows(InvalidAttributeValueException.class,() -> orderService.createOrder(OrderServiceImpl.entityToDTO(order)));
	}
	
	@Test
	public void findOrderByUser() {
		
		Set<Product> products = new HashSet<>();
		User user = new User("abc@gmail.com", "9845456","Sherlock", "Holmes", "*****");
		Product p = new Product("Sugar", "101", "Sweet","Diablis");
		products.add(p);
		Address address = new Address();
		
		Order order = new Order(LocalDate.now(),products,user,address);
		order.setOrderId(1);
		
		
		Mockito.when(orderDao.findByUserEmailId(order.getUser().getEmailId())).thenReturn(Stream.of(order).collect(Collectors.toList()));
		
		OrderDTO orderReturned= OrderServiceImpl.entityToDTO(order);
		List<OrderDTO> ordersExpected = Stream.of(orderReturned).collect(Collectors.toList()); //
		
		List<OrderDTO> actual = orderService.findOrdersByUser("abc@gmail.com");
		assertEquals(ordersExpected,actual);	
	}
	
	@Test
	public void findOrdersNullUserCheck() {
		
		String userId = null;
		
		List<OrderDTO> actual = orderService.findOrdersByUser(userId);
		assertNull(actual);
	}
	
	@Test
	public void deleteOrderNullCheck() {
        
	    Integer orderId = null;
		
		OrderDTO actual = orderService.findOrder(orderId);
		assertNull(actual);
		
	}
	
}
