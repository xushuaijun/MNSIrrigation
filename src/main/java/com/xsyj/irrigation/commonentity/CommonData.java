package com.xsyj.irrigation.commonentity;

import java.io.Serializable;

public class CommonData<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T data;
	private String msg;
	private int s;
	
	public CommonData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonData(T data, String msg, int s) {
		super();
		this.data = data;
		this.msg = msg;
		this.s = s;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return "CommonDataE{" +
				"data=" + data +
				", msg='" + msg + '\'' +
				", s=" + s +
				'}';
	}
}
