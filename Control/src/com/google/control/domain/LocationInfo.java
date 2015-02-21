package com.google.control.domain;

/**
 * 位置信息
 * @author Administrator
 *
 */
public class LocationInfo {
	/**
	 * 纬度
	 */
	private double latitude;
	/**
	 * 经度
	 */
	private double longitude;
	/**
	 * 网络类型
	 */
	private String networkLocationType;
	/**
	 * 范围
	 */
	private int radius;
	
	/**
	 * 速度
	 */
	private int speed;
	/**
	 * 地址
	 */
	private String address;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getNetworkLocationType() {
		return networkLocationType;
	}
	public void setNetworkLocationType(String networkLocationType) {
		this.networkLocationType = networkLocationType;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "LocationInfo [latitude=" + latitude + ", longitude="
				+ longitude + ", networkLocationType=" + networkLocationType
				+ ", radius=" + radius + ", speed=" + speed + ", address="
				+ address + "]";
	}
}
