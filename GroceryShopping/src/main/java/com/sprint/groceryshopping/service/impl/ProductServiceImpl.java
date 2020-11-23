package com.sprint.groceryshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.groceryshopping.dto.ProductDTO;
import com.sprint.groceryshopping.exception.DuplicateRecordFoundException;
import com.sprint.groceryshopping.exception.RecordNotFoundException;
import com.sprint.groceryshopping.model.Product;
import com.sprint.groceryshopping.repositry.ProductDAO;
import com.sprint.groceryshopping.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductDAO productDao;
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	 
	@Override
	public List<ProductDTO> getAllProducts() throws RecordNotFoundException{

		List<ProductDTO> productDTO = new ArrayList<>();
		for(Product c:productDao.findAll())
		{
			productDTO.add(convertEntityToDTO(c));
		}
		if(productDTO.isEmpty())
		{
			log.warn("Records Not Found");
			throw new RecordNotFoundException("Records Not Exists");
		}
		
		log.info("Retrived All Products");
		
		return productDTO;
	}
	
	@Override
	public List<ProductDTO> add(ProductDTO productDto) throws DuplicateRecordFoundException{
		
		if(productDto == null)
		{
			return null;
		}
		
		Optional<Product> checking = productDao.findById(convertDTOToEntity(productDto).getCode());
		if(checking.isPresent())
		{
			log.warn("Records Exists");
			throw new DuplicateRecordFoundException("Product Already Exists");
		}
		Product product = convertDTOToEntity(productDto);
		product = productDao.saveAndFlush(product);
		productDto = convertEntityToDTO(product);
		List<ProductDTO> dto = new ArrayList<>();
		dto.add(productDto);	
		
		log.info("Added Product Successfull..!!!");
		
		return dto;
	}


	@Override
	public List<ProductDTO> delete(long code) throws RecordNotFoundException{
		
		if(code == 0)
		{
			return null;
		}
		Optional<Product>find = productDao.findById(code);
		if(!find.isPresent())
		{
			log.warn("Records Not Found");
			throw new RecordNotFoundException("Records Not Exists");
		}
		productDao.deleteById(code);
		List<ProductDTO> productDto = new ArrayList<>();
		for(Product c:productDao.findAll())
		{
			productDto.add(convertEntityToDTO(c));
		}
		
		log.info("Deleted Product Successfully...!!!");
		
		return productDto;
	}



	@Override
	public ProductDTO findByCode(long code) throws RecordNotFoundException{
		if(code == 0)
		{
			return null;
		}
		Optional<Product>find = productDao.findById(code);
		if(!find.isPresent() || find == null)
		{
			log.warn("Records Not Found");
			throw new RecordNotFoundException("Records Not Exists");
		}
		
		log.info("Product Found...!!!");
		
		return convertEntityToDTO(find.get());
	}
	
	public static Product convertDTOToEntity(ProductDTO productDto)
	{
		Product product = new Product();
		
		product.setCode(productDto.getCode());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		product.setBrand(productDto.getBrand());
		
		return product;
	}
	
	public List<Product> convertDTOToEntity(List<ProductDTO> productDto)
	{
		return productDto.stream().map(x -> convertDTOToEntity(x)).collect(Collectors.toList());
	}
	
	public static ProductDTO convertEntityToDTO(Product product)
	{
		ProductDTO productDto = new ProductDTO();
		
		productDto.setCode(product.getCode());
		productDto.setName(product.getName());
		productDto.setPrice(product.getPrice());
		productDto.setDescription(product.getDescription());
		productDto.setBrand(product.getBrand());
		
		
		return productDto;
	}
	
	public List<ProductDTO> convertEntityToDTO(List<Product> product)
	{
		return product.stream().map(x -> convertEntityToDTO(x)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> update(ProductDTO bean) throws RecordNotFoundException{
		if(bean == null)
		{
			return null;
		}
		Product product = convertDTOToEntity(bean);
		product = productDao.save(product);
		bean = convertEntityToDTO(product);
		List products = new ArrayList<>();
		products.add(bean);
		if(bean == null)
		{
			log.warn("Records Not Found");
			throw new RecordNotFoundException("Record not found");
		}
		
		log.info("Updated Product");
		
		return products;
	}


}