package com.google.control.domain;
/**
 * 联系人Bean
 * @author Administrator
 *
 */
public class ContactInfo {
	private int id;
	private String name;
	private String number;
	public ContactInfo(String name, String number) {
		this.name=name;
		this.number=number;
	}
	public ContactInfo() {
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "ContactInfo [id=" + id + ", name=" + name + ", number="
				+ number + "]";
	}

}
