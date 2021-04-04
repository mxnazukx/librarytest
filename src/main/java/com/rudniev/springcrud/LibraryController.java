package com.rudniev.springcrud;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/usersbooks")
	public List<String> listUsersAndBooks(){
		List<String> list = new ArrayList<>();
		List<Book> books = bookService.listAll();
		books.stream().filter(b -> b.getUser_id() != 0).collect(Collectors.toList()).sort((b1, b2) -> b1.getUser_id()-b2.getUser_id());
		
		for(User u : userService.listAll()) {
			String str = "User name: " + u.getName() + " Books: ";
			for(Book b : books) {
				if(b.getUser_id() == u.getId()) str += b.getName() + " ";
			}
			list.add(str);
		}
		return list;
	}
	
	@GetMapping("/books")
	public List<Book> listBooks() {
		return bookService.listAll();
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Integer id) {
		try {
			Book book = bookService.get(id);
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/books")
	public void addBook(@RequestBody Book book) {
		if(book.getUser_id() == 0) book.setFree(1);
		else if(book.getUser_id() != 0) book.setFree(0);
		bookService.save(book);
		
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<?> updateBook(@RequestBody Book book,
			@PathVariable Integer id) {
		try {
			Book existBook = bookService.get(id);
			existBook.setName(book.getName());
			bookService.save(existBook);
			return new ResponseEntity<Book>(HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	@PutMapping("/books/{id}/take")
	public ResponseEntity<?> takeBook(@RequestBody Book book,
			@PathVariable Integer id) {
		try {
			Book existBook = bookService.get(id);
			if(existBook.getFree() == 1 && book.getUser_id() != 0) {
				existBook.setFree(0);
				existBook.setUser_id(book.getUser_id());
				
				bookService.save(existBook);
			}
			return new ResponseEntity<Book>(HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	@PutMapping("/books/{id}/return")
	public ResponseEntity<?> returnBook(@RequestBody Book book,
			@PathVariable Integer id) {
		try {
			Book existBook = bookService.get(id);
			if(existBook.getFree() == 0) {
				book.setName(existBook.getName());
				book.setFree(1);
				book.setUser_id(0);
				bookService.save(book);
			}
			return new ResponseEntity<Book>(HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable Integer id) {
		bookService.delete(id);
	}
	
	
	@GetMapping("/users")
	public List<User> listUsers() {
		return userService.listAll();
	}
	
	@GetMapping("/users/{id}/books")
	public List<Book> listUserByIdBooks(@PathVariable Integer id){
		return bookService.listAll().stream().filter(b -> b.getUser_id() == id).collect(Collectors.toList());
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		try {
			User user = userService.get(id);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/users")
	public void addUser(@RequestBody User user) {
		userService.save(user);
		
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User user,
			@PathVariable Integer id) {
		try {
			User existUser = userService.get(id);
			existUser.setName(user.getName());
			userService.save(existUser);
			return new ResponseEntity<User>(HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		try {
			userService.get(id);
			userService.delete(id);
			return new ResponseEntity<User>(HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
}
