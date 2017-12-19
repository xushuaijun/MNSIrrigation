package com.xsyj.irrigation.entity;

import java.io.Serializable;


/**
 *
 * @author Administrator
 *
 */

public class IrrigationCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 累计总用水量
	 */
	private double totalWater;
	/**
	 *总灌溉时间
	 */
	private double totalTime;
	/**
	 * 总灌溉次数
	 */
	private double totalTimes;
	
	private int trunId;
	private int pileId;
	private int state;
	private String userId;
	private int tapState;
	
	
	public int getTapState() {
		return tapState;
	}
	public void setTapState(int tapState) {
		this.tapState = tapState;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTrunId() {
		return trunId;
	}
	public void setTrunId(int trunId) {
		this.trunId = trunId;
	}
	public int getPileId() {
		return pileId;
	}
	public void setPileId(int pileId) {
		this.pileId = pileId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public IrrigationCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IrrigationCount(double totalWater, double totalTime, double totalTimes) {
		super();
		this.totalWater = totalWater;
		this.totalTime = totalTime;
		this.totalTimes = totalTimes;
	}
	public double getTotalWater() {
		return totalWater;
	}
	public void setTotalWater(double totalWater) {
		this.totalWater = totalWater;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	public double getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(double totalTimes) {
		this.totalTimes = totalTimes;
	}
	@Override
	public String toString() {
		return "IrrigationCount [totalWater=" + totalWater + ", totalTime=" + totalTime + ", totalTimes=" + totalTimes
				+ ", trunId=" + trunId + ", pileId=" + pileId + ", state=" + state + ", userId=" + userId + "]";
	}

	
	

}
