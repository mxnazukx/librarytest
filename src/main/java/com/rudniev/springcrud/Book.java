package com.rudniev.springcrud;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "book")
public class Book {
	private int id;
	private String name;
	
	private int user_id;
	
	private int free;

	public Book() {
	}
	
	
	
	public Book(int id, String name, int user_id, int free) {
		super();
		this.id = id;
		this.name = name;
		this.user_id = user_id;
		this.free = free;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getFree() {
		return free;
	}

	public void setFree(int free) {
		this.free = free;
	}

}
