package com.sprint.groceryshopping.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sprint.groceryshopping.dto.UserDTO;
import com.sprint.groceryshopping.exception.DuplicateRecordFoundException;
import com.sprint.groceryshopping.exception.RecordNotFoundException;
import com.sprint.groceryshopping.model.User;
import com.sprint.groceryshopping.repositry.UserDAO;


@SpringBootTest
public class UserServiceImplTest {
	
	@MockBean
	UserDAO userDao;
	
	@Autowired
	UserServiceImpl userService;

	@Test
	public void addNullTest() {
		
		UserDTO user = null;
		List<UserDTO> actual = userService.add(user);
		Assertions.assertNull(actual);
	}

	@Test
	public void addUserEntityTest() throws DuplicateRecordFoundException{
		
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		Mockito.when(userDao.saveAndFlush(user)).thenReturn(user);  
		Mockito.when(userDao.findAll()).thenReturn(Stream.of(user).collect(Collectors.toList()));
		List<UserDTO> actual = userService.getAllUsers();
		assertEquals(1,actual.size());
	}
	
	@Test
	public void userAlreadyExistsTest() {	
		
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		Mockito.when(userDao.saveAndFlush(user)).thenThrow(new DuplicateRecordFoundException("User Already Exists"));
		Exception exception = Assertions.assertThrows(DuplicateRecordFoundException.class, () -> userDao.saveAndFlush(user));
		assertEquals("User Already Exists",exception.getMessage());	
	}

	
	@Test
	public void deleteNullTest() {
		
		String emailId = null;
		List<UserDTO> actual = userService.delete(emailId);
		Assertions.assertNull(actual);
	}
	
	@Test
	public void deleteByIdTest() throws RecordNotFoundException{
		
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		String id = user.getEmailId();
		Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));
		List<UserDTO> actual=userService.delete(id);
		assertEquals(0,actual.size());
	}
	
	@Test
	public void deleteByIdNotPresentTest() {	
		
		Mockito.when(userDao.findById("aaaa@gmail.com")).thenThrow(new RecordNotFoundException("User Not Found"));
		Exception exception = assertThrows(RecordNotFoundException.class, () -> userService.delete("aaaa@gmail.com"));
		assertEquals("User Not Found",exception.getMessage());
	}
	
	@Test
	public void updateNullTest() {
		
		UserDTO user = null;
		List<UserDTO> actual = userService.add(user);
		Assertions.assertNull(actual);
	}
	@Test
	public void updateUserNameTest() throws RecordNotFoundException{
		
		String firstName = "doraemon";
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		user.setFirstName(firstName);
		Mockito.when(userDao.saveAndFlush(user)).thenReturn(user); 
		Mockito.when(userDao.findAll()).thenReturn(Stream.of(user).collect(Collectors.toList()));		
		List<UserDTO> actual1 = userService.getAllUsers();
		assertEquals(firstName,actual1.get(0).getFirstName());
	}
	
	@Test
	public void updateMobileNumberTest() throws RecordNotFoundException{
		
		String mobileNo = "9999999999";
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		user.setMobileNo(mobileNo);
		Mockito.when(userDao.saveAndFlush(user)).thenReturn(user); 
		Mockito.when(userDao.findAll()).thenReturn(Stream.of(user).collect(Collectors.toList()));		
		List<UserDTO> actual1 = userService.getAllUsers();
		assertEquals(mobileNo,actual1.get(0).getMobileNo());
	}
	
	@Test
	public void updateMobileNotPresentTest() {	
		
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		String mobileNo = "9999999999";
		user.setMobileNo(mobileNo);
		Mockito.when(userDao.saveAndFlush(user)).thenThrow(new RecordNotFoundException("User Not Found"));
		Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> userDao.saveAndFlush(user));
		assertEquals("User Not Found",exception.getMessage());
		
	}
	
	@Test
	public void getAllUsersTest() {
		
		UserDTO user1 = new UserDTO("Tom@gmail.com","9876543210","Tom", "Jhon","aaaa");
		UserDTO user2 = new UserDTO("Tomm@gmail.com","9876543210","Tom", "Jhon","aaaa");
		Mockito.when(userDao.findAll()).thenReturn(Stream.of(UserServiceImpl.convertDTOToEntity(user1),UserServiceImpl.convertDTOToEntity(user2)).collect(Collectors.toList()));
		List<UserDTO> actual = userService.getAllUsers();
		assertEquals(2,actual.size());
	}
	
	@Test
	public void getAllUsersNotPresentTest() {	
		
		//UserDTO user1 = new UserDTO("Tom@gmail.com",236,"9876543210","Tom", "Jhon","aaaa");
		//UserDTO user2 = new UserDTO("Tomm@gmail.com",236,"9876543210","Tom", "Jhon","aaaa");
		Mockito.when(userDao.findAll()).thenThrow(new RecordNotFoundException("User Not Found"));
		Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> userService.getAllUsers());
		assertEquals("User Not Found",exception.getMessage());
		
	}
	
	@Test
	public void getAllUsersNullTest() throws RecordNotFoundException {
		
		List<User> actual = userDao.findAll();
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void signInSuccessTest() {
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		String id = user.getEmailId();
		String password = user.getPassword();
		Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));
		UserDTO actual = userService.findByLogin(id);
		String id1 = actual.getEmailId();
		String password1 = actual.getPassword();
		assertEquals(id,id1);
		assertEquals(password,password1);	
	}
	
	@Test
	public void signInNullTest() throws RecordNotFoundException {
		
			UserDTO user = null;	
			UserDTO actual = userService.signIn(user);			
			assertNull(actual);
		}
	@Test
	public void findByLoginNullTest() throws RecordNotFoundException {
		
		String user = null;
		Mockito.when(userDao.findById(user)).thenReturn(null);
		UserDTO result = userService.findByLogin(user);
		assertNull(result);
	}
	
	@Test
	public void findByLoginTest() {
		
		User user = new User("Tomm@gmail.com","9876543210","Tom", "John","aaaa");
		String id = user.getEmailId();
		Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));
		UserDTO actual = userService.findByLogin(id);
		assertEquals(id, actual.getEmailId());
		
	}
	
	@Test
	public void findByLoginNotPresentTest() {	
		
		Mockito.when(userDao.findById("aaaa@gmail.com")).thenThrow(new RecordNotFoundException("User Not Found"));
		Exception exception = assertThrows(RecordNotFoundException.class, () -> userService.findByLogin("aaaa@gmail.com"));
		assertEquals("User Not Found",exception.getMessage());
	}
}