package com.sprint.groceryshopping.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.sprint.groceryshopping.dto.ProductDTO;


@Service
public interface IProductService {

	public List<ProductDTO> add(ProductDTO bean);

	public List<ProductDTO> update(ProductDTO bean);

	public List<ProductDTO> delete(long code);

	public ProductDTO findByCode(long code);

	public List<ProductDTO> getAllProducts();

}