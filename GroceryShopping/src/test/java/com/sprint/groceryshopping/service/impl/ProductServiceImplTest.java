package com.sprint.groceryshopping.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sprint.groceryshopping.dto.ProductDTO;
import com.sprint.groceryshopping.exception.DuplicateRecordFoundException;
import com.sprint.groceryshopping.exception.RecordNotFoundException;
import com.sprint.groceryshopping.model.Product;
import com.sprint.groceryshopping.repositry.ProductDAO;
import com.sprint.groceryshopping.service.IProductService;
import com.sprint.groceryshopping.service.impl.ProductServiceImpl;


@SpringBootTest
public class ProductServiceImplTest {
	
	@MockBean
	ProductDAO productDao;
	
	@Autowired
	IProductService productService;

	
	@Test
	public void addNull() {
		ProductDTO product = null;
		List<ProductDTO> actual = productService.add(product);
		assertNull(actual);
	}

	@Test//working
	public void addProductEntity() {
		Product product = new Product("Sugar", "101", "Sweet","Diablis");
		product.setCode(108);
		Mockito.when(productDao.save(product)).thenReturn(product);  
		Mockito.when(productDao.findAll()).thenReturn(Stream.of(product).collect(Collectors.toList()));
		List<ProductDTO> actual = productService.getAllProducts();
		assertEquals(1,actual.size());
	}
	
	@Test//working
	public void AddProductAlreadyExists() {	
		Product product = new Product("Sugar", "101", "Sweet","Diablis");
		product.setCode(101);
		Mockito.when(productDao.saveAndFlush(product)).thenThrow(new DuplicateRecordFoundException("Product Already Exists"));
		ProductDTO dto = ProductServiceImpl.convertEntityToDTO(product);
		Exception exception = assertThrows(DuplicateRecordFoundException.class, () -> productDao.saveAndFlush(product));
		assertEquals("Product Already Exists",exception.getMessage());
		
	}

	@Test
	public void updateNull() {
		ProductDTO user = null;
		
		List<ProductDTO> actual = productService.add(user);
		assertNull(actual);
	}
	

	@Test//working
	public void updateProductName() throws RecordNotFoundException{
		String name = "doraemon";
		Product product = new Product("Sugar", "101", "Sweet","Diablis");
		product.setName(name);
		Mockito.when(productDao.saveAndFlush(product)).thenReturn(product); 
		Mockito.when(productDao.findAll()).thenReturn(Stream.of(product).collect(Collectors.toList()));		
		List<ProductDTO> actual1 = productService.getAllProducts();
		assertEquals(name,actual1.get(0).getName());
	}
	
	
	@Test//working
	public void updateBrand() throws RecordNotFoundException{
		String brand = "Sugarflakes";
		Product product = new Product("Sugar", "101", "Sweet","Diablis");
		product.setBrand(brand);
		Mockito.when(productDao.saveAndFlush(product)).thenReturn(product); 
		Mockito.when(productDao.findAll()).thenReturn(Stream.of(product).collect(Collectors.toList()));		
		List<ProductDTO> actual1 = productService.getAllProducts();
		assertEquals(brand,actual1.get(0).getBrand());
	}

	@Test//working
	public void UpdateNameNotPresent() {	
		Product product = new Product("Sugar", "101", "Sweet","Diablis");
		String name = "book";
		Mockito.when(productDao.saveAndFlush(product)).thenThrow(new RecordNotFoundException("Record Not Exists"));
		Exception exception = assertThrows(RecordNotFoundException.class, () -> productDao.saveAndFlush(product));
		assertEquals("Record Not Exists",exception.getMessage());
	}
	
	@Test
	public void getProductsNull() throws RecordNotFoundException {
		List<Product> actual = productDao.findAll();
		assertTrue(actual.isEmpty());
	}
	
	
	@Test
	public void getAllProductTest() 
	{
       Product product = new Product("es", "ft", "gy", "gjy");
		Mockito.when(productDao.findAll()).thenReturn(Stream.of(product).collect(Collectors.toList()));
		List<ProductDTO> actual = productService.getAllProducts();
		assertEquals(1,actual.size());
	}
	
	@Test
	public void getAllProductsNotPresent() {	
		Mockito.when(productDao.findAll()).thenThrow(new RecordNotFoundException("Records Not Exists"));
		Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.getAllProducts());
		assertEquals("Records Not Exists",exception.getMessage());
		
	}
	
	@Test
	public void findByCodeNullTest() throws RecordNotFoundException {
		Long product = (long) 0;
		Mockito.when(productDao.findById(product)).thenReturn(null);
		ProductDTO result = productService.findByCode(product);
		assertNull(result);
	}
	
	@Test
	public void findByCode() {
		Product product = new Product("Sugar", "101", "Sweet","Diablis");
		product.setCode(101);
		Mockito.when(productDao.findById((long) 101)).thenReturn(Optional.of(product));
		ProductDTO actual = productService.findByCode(101);
		assertEquals(101, actual.getCode());
		
	}
	
	@Test
	public void findByCodeNotPresent() {	
		Long id = (long) 101;
		Mockito.when(productDao.findById(id)).thenThrow(new RecordNotFoundException("Records Not Exists"));
		Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.findByCode(id));
		assertEquals("Records Not Exists",exception.getMessage());
	}
	
	
	@Test
	public void deleteNull() {
		Long id = (long) 0;
		List<ProductDTO> actual = productService.delete(id);
		assertNull(actual);
	}
	
	@Test
	public void deleteByIdNotPresent() {	
		Long id = (long) 101;
		Mockito.when(productDao.findById(id)).thenThrow(new RecordNotFoundException("Records Not Exists"));
		Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.delete(id));
		assertEquals("Records Not Exists",exception.getMessage());
	}

}
