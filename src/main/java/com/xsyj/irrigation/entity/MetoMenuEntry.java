package com.xsyj.irrigation.entity;

import java.io.Serializable;

public class MetoMenuEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String imgsrc;
	private int count;
	public MetoMenuEntry() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MetoMenuEntry(String title, String imgsrc, int count) {
		super();
		this.title = title;
		this.imgsrc = imgsrc;
		this.count = count;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
