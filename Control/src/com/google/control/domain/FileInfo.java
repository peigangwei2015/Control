package com.google.control.domain;

/**
 * @author Administrator
 *封装文件信息
 */
public class FileInfo {
	private String path;
	private String name;
	private long size;
	private long createDate;
	private boolean isDir;
	private String parent;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	public boolean isDir() {
		return isDir;
	}
	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "FileInfo [path=" + path + ", name=" + name + ", size=" + size
				+ ", createDate=" + createDate + ", isDir=" + isDir
				+ ", parent=" + parent + "]";
	}
	
}
