package com.rudniev.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudniev.springcrud.Application;
import com.rudniev.springcrud.BookRepository;
import com.rudniev.springcrud.LibraryController;
import com.rudniev.springcrud.User;
import com.rudniev.springcrud.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
@ContextConfiguration(classes={Application.class})
@ComponentScan(basePackages = {"com.rudniev.springcrud"})
public class BookServiceTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private BookRepository bookRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testListUsers() throws Exception {
		List<User> listUsers = new ArrayList<>();
		listUsers.add(new User(1, "Oleg"));
		listUsers.add(new User(2, "Ivan"));
		listUsers.add(new User(3, "Daria"));
		listUsers.add(new User(4, "Nazar"));
		
		Mockito.when(userRepository.findAll()).thenReturn(listUsers);
		
		String url = "/users";
		
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsnoResponse = mvcResult.getResponse().getContentAsString();
		
		String expectedJsonResponse = objectMapper.writeValueAsString(listUsers);
		
		assertEquals(actualJsnoResponse, expectedJsonResponse);
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		User user = new User("Oleg");
		User savedUser = new User(1, "Ivan");
		
		Mockito.when(userRepository.save(user)).thenReturn(savedUser);
		String url = "/users";
		mockMvc.perform(
				post(url).contentType("application/json").content(objectMapper.writeValueAsString(user))).andExpect(status().isOk());
	}
	
}
