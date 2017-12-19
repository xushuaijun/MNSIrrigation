package com.xsyj.irrigation.entity;

import java.util.Date;
import java.util.List;

public class IrrigationTaskTurn {
    
	private Integer id;
	private Integer turnid;
	private Date addtime;
	private Integer starttype;
	private Date starttime;
	private Date endtime;
	private String lstarttime;
	private String lendtime;
	private Integer irrigationcount;
	private Integer state;
	private String userid;
	private String stime;
	private String etime;
	private String turnname;
	private String pileName;


	public IrrigationTaskTurn() {
	}

	public IrrigationTaskTurn(Integer id, Integer turnid, Date addtime, Integer starttype, Date starttime, Date endtime, Integer irrigationcount, Integer state, String userid, String stime, String etime, String turnname, String pileName) {
		this.id = id;
		this.turnid = turnid;
		this.addtime = addtime;
		this.starttype = starttype;
		this.starttime = starttime;
		this.endtime = endtime;
		this.irrigationcount = irrigationcount;
		this.state = state;
		this.userid = userid;
		this.stime = stime;
		this.etime = etime;
		this.turnname = turnname;
		this.pileName = pileName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTurnid() {
		return turnid;
	}

	public void setTurnid(Integer turnid) {
		this.turnid = turnid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getStarttype() {
		return starttype;
	}

	public void setStarttype(Integer starttype) {
		this.starttype = starttype;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getLstarttime() {
		return lstarttime;
	}

	public void setLstarttime(String lstarttime) {
		this.lstarttime = lstarttime;
	}

	public String getLendtime() {
		return lendtime;
	}

	public void setLendtime(String lendtime) {
		this.lendtime = lendtime;
	}

	public Integer getIrrigationcount() {
		return irrigationcount;
	}

	public void setIrrigationcount(Integer irrigationcount) {
		this.irrigationcount = irrigationcount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getTurnname() {
		return turnname;
	}

	public void setTurnname(String turnname) {
		this.turnname = turnname;
	}

	public String getPileName() {
		return pileName;
	}

	public void setPileName(String pileName) {
		this.pileName = pileName;
	}

	@Override
	public String toString() {
		return "IrrigationTaskTurn{" +
				"id=" + id +
				", turnid=" + turnid +
				", addtime=" + addtime +
				", starttype=" + starttype +
				", starttime=" + starttime +
				", endtime=" + endtime +
				", lstarttime='" + lstarttime + '\'' +
				", lendtime='" + lendtime + '\'' +
				", irrigationcount=" + irrigationcount +
				", state=" + state +
				", userid='" + userid + '\'' +
				", stime='" + stime + '\'' +
				", etime='" + etime + '\'' +
				", turnname='" + turnname + '\'' +
				", pileName='" + pileName + '\'' +
				'}';
	}
}