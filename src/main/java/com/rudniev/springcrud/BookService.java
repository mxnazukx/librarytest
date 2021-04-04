package com.rudniev.springcrud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	@Autowired
	private BookRepository repo;
	
	public List<Book> listAll(){
		return repo.findAll();
	}
	
	public void save(Book book) {
		repo.save(book);
	}
	
	public Book get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
}
