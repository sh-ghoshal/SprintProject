package com.sprint.groceryshopping.service;




import java.util.List;


import org.springframework.stereotype.Service;

import com.sprint.groceryshopping.dto.AddressDTO;
import com.sprint.groceryshopping.exception.RecordNotFoundException;




@Service
public interface IAddressService {
	
	public AddressDTO add(AddressDTO address) ; 
	
	public AddressDTO update(AddressDTO address) ;
	
	public AddressDTO delete(Integer id) throws  RecordNotFoundException;

	public AddressDTO findById(Integer addressId);

	public List<AddressDTO> getAddress();

}


