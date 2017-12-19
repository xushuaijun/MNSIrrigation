package com.xsyj.irrigation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CirclePlan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;              //唯一标示
	private String choiceName;   //周期
	private String pileName;     //出地桩id
	private String startTime;    //开始时间
	private String endTime;      //结束时间
	private List<CirclePlan> children;
	
	public CirclePlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CirclePlan(int id, String choiceName, String pileName, String startTime, String endTime,
			List<CirclePlan> children) {
		super();
		this.id = id;
		this.choiceName = choiceName;
		this.pileName = pileName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChoiceName() {
		return choiceName;
	}

	public void setChoiceName(String choiceName) {
		this.choiceName = choiceName;
	}

	public String getPileName() {
		return pileName;
	}

	public void setPileName(String pileName) {
		this.pileName = pileName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<CirclePlan> getChildren() {
		return children;
	}

	public void setChildren(List<CirclePlan> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "CirclePlan [id=" + id + ", choiceName=" + choiceName + ", pileName=" + pileName + ", startTime="
				+ startTime + ", endTime=" + endTime + ", children=" + children + "]";
	}
	
	
	

}
