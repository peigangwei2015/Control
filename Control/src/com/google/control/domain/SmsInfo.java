package com.google.control.domain;

import android.R.integer;

/**
 * 短信Bean
 */
public class SmsInfo {
	private String body;
	private long date;
	private int id;
	private String address;
	private String name;
	private int read;
	private int type;
	public SmsInfo() {
		super();
	}
	public String getBody() {
		return body;
	}
	public int getId() {
		return id;
	}
	public int getType() {
		return type;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	@Override
	public String toString() {
		return "SmsInfo [body=" + body + ", date=" + date + ", id=" + id
				+ ", address=" + address + ", name=" + name + ", read=" + read
				+ ", type=" + type + "]";
	}
	
}
