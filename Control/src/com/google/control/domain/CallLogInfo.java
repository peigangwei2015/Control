package com.google.control.domain;

/**
 * @author Administrator
 *通话记录实体类
 */
public class CallLogInfo {
	private int duration;
	private int id;
	private String name;
	private String number;
	private int type;
	private long date;
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "CallLogInfo [duration=" + duration + ", id=" + id + ", name="
				+ name + ", number=" + number + ", type=" + type + ", date="
				+ date + "]";
	}
	
}
