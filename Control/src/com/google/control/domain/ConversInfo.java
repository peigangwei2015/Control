package com.google.control.domain;

/**
 * 短信会话Bean
 */
public class ConversInfo {
	private int id;
	private long date;
	private int count;
	private String body;
	private String address;
	private String name;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "ConversInfo [id=" + id + ", date=" + date + ", count=" + count
				+ ", body=" + body + ", address=" + address + ", name=" + name
				+ "]";
	}
}
