 package com.sprint.groceryshopping.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.sprint.groceryshopping.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	
	@Autowired
	OrderDAO orderDao;
	
	public static OrderDTO entityToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setDate(order.getDate());
		orderDTO.setAmount(order.getAmount());
		
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(order.getUser().getFirstName());
		userDTO.setLastName(order.getUser().getLastName());
		userDTO.setMobileNo(order.getUser().getMobileNo());
		userDTO.setEmailId(order.getUser().getEmailId());
		userDTO.setPassword(order.getUser().getPassword());
		userDTO.setOrders(null);
		
		orderDTO.setUser(userDTO);
		
		Set<ProductDTO> productsDTO = new HashSet<>();
		Set<Product> products = order.getProducts();
		for (Product p : products ) {
			
			ProductDTO productDTO = new ProductDTO();
			productDTO.setCode(p.getCode());
			productDTO.setName(p.getName());
			productDTO.setDescription(p.getDescription());
			productDTO.setPrice(p.getPrice());
			productDTO.setBrand(p.getBrand());
			productsDTO.add(productDTO);
		
		}
		
		orderDTO.setProducts(productsDTO);
		
		AddressDTO address = new AddressDTO();
		address.setAddressId(order.getAddress().getAddressId());
		address.setCountry(order.getAddress().getCountry());
		address.setDistrict(order.getAddress().getDistrict());
		address.setPinCode(order.getAddress().getPinCode());
		address.setState(order.getAddress().getState());

		
		

		orderDTO.setAddress(address);
		
		return orderDTO;
	}
	
	public static Order DTOToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderId(orderDTO.getOrderId());
		order.setDate(orderDTO.getDate());
		order.setAmount(orderDTO.getAmount());
		
		User user = new User();
		user.setFirstName(orderDTO.getUser().getFirstName());
		user.setLastName(orderDTO.getUser().getLastName());
		user.setMobileNo(orderDTO.getUser().getMobileNo());
		user.setEmailId(orderDTO.getUser().getEmailId());
		user.setPassword(orderDTO.getUser().getPassword());
		user.setOrders(null);
		
		order.setUser(user);
		
		Set<ProductDTO> productsDTO = orderDTO.getProducts();

		Set<Product> products = new HashSet<>();
		for (ProductDTO p : productsDTO ) {
			
			Product product = new Product();
			product.setCode(p.getCode());
			product.setName(p.getName());
			product.setDescription(p.getDescription());
			product.setPrice(p.getPrice());
			product.setBrand(p.getBrand());
			products.add(product);
		}
		
		order.setProducts(products);
		
		Address address = new Address();
		address.setAddressId(orderDTO.getAddress().getAddressId());
		address.setCountry(orderDTO.getAddress().getCountry());
		address.setDistrict(orderDTO.getAddress().getDistrict());
		address.setPinCode(orderDTO.getAddress().getPinCode());
		address.setState(orderDTO.getAddress().getState());
		
			
        order.setAddress(address);
		
		return order;
	}
	
	@Override
	public List<OrderDTO> getAllOrders() throws DatabaseException {
		
		List<Order> orders =  orderDao.findAll();
		if(orders.size()<1) {
			
			log.error("No orders in database!");
			throw new DatabaseException();
		}
		
		List<OrderDTO> ordersReturn = new ArrayList<>();
		for (Order o : orders ) {
			ordersReturn.add(entityToDTO(o));
		}
		
		log.info("Orders fetched!");
		return ordersReturn;
	}

	@Override
	public OrderDTO findOrder(Integer orderId) throws OrderNotFoundException {
		if(orderId == null) {
			log.info("Please enter valid orderId.");
			return null;
		}
		
		Optional<Order> order = orderDao.findById(orderId);
		
		if(!order.isPresent() || order == null) {
			log.error("Order not present.");
			throw new OrderNotFoundException();
		}
		
		log.info("Order fetched!");
		return entityToDTO(order.get());
	}
	
	@Override
	public List<OrderDTO> findOrdersByDate (LocalDate date) throws OrderNotFoundException, DatabaseException{
		if(date == null) {
			log.info("Please enter valid orderId.");
			return null;
		}
		
		List<Order> orders;
		try {
			
			log.warn("Checking for order by date");
		    orders = orderDao.findByDate(date);
		}
		catch(Exception e) {
			log.warn("Order not present.");
			throw new OrderNotFoundException();
		}
	
		List<OrderDTO> ordersReturn = new ArrayList<>();
		for (Order o : orders ) {
			ordersReturn.add(entityToDTO(o));
		}
		
		log.info("Order fetched");
		return ordersReturn;	
		
	}


	@Override
	public String deleteOrder(Integer orderId) throws OrderNotFoundException {
		if(orderId == null) {
			log.info("Enter valid id");
			return null;
		}
		
		try {
			
			log.warn("Checking for orderID to be deleted.");
		    orderDao.deleteById(orderId);
		}
		
		catch(Exception e) {
			log.error("Order not present");
			throw new OrderNotFoundException();
		}
		
		return "Order deleted!";	
	}

	@Override
	public List<OrderDTO> createOrder(OrderDTO orderDTO) throws OrderNotFoundException, DatabaseException{
		if(orderDTO == null) {
			log.info("Please enter valid orderId.");
			return null;
		}
		
		System.out.println(orderDTO);
	
		Order order = DTOToEntity(orderDTO);
		System.out.println(order);

		orderDao.saveAndFlush(order);
		
		List<Order> orders = orderDao.findAll();
		if(orders.size()<1) {
			
			log.error("No orders in database! Please check");
			throw new OrderNotFoundException();
		}
		List<OrderDTO> ordersReturn = new ArrayList<>();
		for (Order o : orders ) {
			ordersReturn.add(entityToDTO(o));
		}
		
		return ordersReturn;	
	}

	@Override
	public List<OrderDTO> updateOrder(OrderDTO orderDTO) throws OrderNotFoundException, DatabaseException{
		if(orderDTO == null) {
			log.info("Please enter valid orderId.");
			return null;
		}
		
		Optional<Order> order = orderDao.findById(orderDTO.getOrderId());
		
		if(!order.isPresent() || order == null) {
			log.info("Order not present");
			throw new OrderNotFoundException();
		}
		
        orderDao.save(DTOToEntity(orderDTO));
		
		List<OrderDTO> ordersReturn = new ArrayList<>();
		List<Order> orders = orderDao.findAll();
		
		if(orders.size()<1) {
			log.error("No orders in database! Please check");
			throw new OrderNotFoundException();
	    }
	
		for (Order o : orders ) {
			ordersReturn.add(entityToDTO(o));
		}
		
		return ordersReturn;
	}
			

	@Override
	public List<OrderDTO> findOrdersByUser(String emailId) throws DatabaseException{
		if(emailId == null) {
			log.info("Please enter valid orderId.");
			return null;
		}
		
		List<Order> orders = new ArrayList<>();
		orderDao.findByUserEmailId(emailId).forEach(orders::add);
		
		if(orders.size()<1 || orders == null) {
			
			log.error("No order details found");
			throw new DatabaseException();
		}
		
		List<OrderDTO> ordersReturn = new ArrayList<>();
		for (Order o : orders ) {
			ordersReturn.add(entityToDTO(o));
		}
		
		return ordersReturn;
	}

}
