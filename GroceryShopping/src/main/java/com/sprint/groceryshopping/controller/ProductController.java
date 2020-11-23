package com.sprint.groceryshopping.controller;

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
import com.sprint.groceryshopping.dto.ProductDTO;
import com.sprint.groceryshopping.exception.RecordNotFoundException;
import com.sprint.groceryshopping.service.IProductService;

@RestController
@RequestMapping("api/v1")
public class ProductController {

	@Autowired
	private IProductService productService;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllCustomers() throws RecordNotFoundException {
		
		List<ProductDTO> product = productService.getAllProducts();

		log.info("Retrived All Products");

		return new ResponseEntity<List<ProductDTO>>(product, HttpStatus.OK);
	}


	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDTO> findProduct(@Valid @PathVariable("productId") long code) throws RecordNotFoundException {
		ProductDTO product = productService.findByCode(code);
	
		log.info("Product Found...!!!");
		return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
	}


	@PostMapping("/products")
	public ResponseEntity<List<ProductDTO>> add(@Valid @RequestBody ProductDTO productDto) {
		List<ProductDTO> product = productService.add(productDto);

		log.info("Added Product Successfull..!!!");
		return new ResponseEntity<List<ProductDTO>>(product, HttpStatus.OK);
	}

	
	@PutMapping("/products")
	public ResponseEntity<List<ProductDTO>> update(@Valid @RequestBody ProductDTO product) {
		List<ProductDTO> products = productService.update(product);

		log.info("Updated Product");
		return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
	}


	@DeleteMapping("/products/{code}")
	public ResponseEntity<List<ProductDTO>> delete(@Valid @PathVariable("code") long productCode) {
		List<ProductDTO> products = productService.delete(productCode);
		
		log.info("Deleted Product Successfully...!!!");
		return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
	}
}