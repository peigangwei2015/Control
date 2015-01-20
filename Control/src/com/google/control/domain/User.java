package com.google.control.domain;

import java.io.Serializable;

/*
 *用户的实体类 
 */
public class User implements Serializable{
	private int id;
	private String name;
	private boolean isOnline;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", isOnline=" + isOnline
				+ "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
