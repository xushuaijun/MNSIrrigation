package com.xsyj.irrigation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MyNodeBean implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 区域ID This field was generated by MyBatis Generator. This field
	 * corresponds to the database column T_SYS_AREA.AREA_ID
	 *
	 * @mbggenerated Mon May 05 14:40:04 CST 2014
	 */
	private String areaId;
	/**
	 * 区域编号 This field was generated by MyBatis Generator. This field
	 * corresponds to the database column T_SYS_AREA.AREA_CODE
	 *
	 * @mbggenerated Mon May 05 14:40:04 CST 2014
	 */
	private String areaCode;
	/**
	 * 父区域编号 This field was generated by MyBatis Generator. This field
	 * corresponds to the database column T_SYS_AREA.PREAT_AREA_ID
	 *
	 * @mbggenerated Mon May 05 14:40:04 CST 2014
	 */
	private String preatAreaId;
	/**
	 * 区域名称 This field was generated by MyBatis Generator. This field
	 * corresponds to the database column T_SYS_AREA.AREA_NAME
	 *
	 * @mbggenerated Mon May 05 14:40:04 CST 2014
	 */
	private String areaName;
	/**
	 * 区域级别 This field was generated by MyBatis Generator. This field
	 * corresponds to the database column T_SYS_AREA.AREA_LEVEL
	 *
	 * @mbggenerated Mon May 05 14:40:04 CST 2014
	 */
	private String areaLevel;

	private String type;


	public MyNodeBean(String areaId, String preatAreaId, String areaName, String type){
		super();
		this.areaId = areaId;
		this.preatAreaId = preatAreaId;
		this.areaName = areaName;
	    this.type = type;
	}


	protected MyNodeBean(Parcel in) {
		areaId = in.readString();
		areaCode = in.readString();
		preatAreaId = in.readString();
		areaName = in.readString();
		areaLevel = in.readString();
	}


	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPreatAreaId() {
		return preatAreaId;
	}

	public void setPreatAreaId(String preatAreaId) {
		this.preatAreaId = preatAreaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MyNodeBean)
		{
			MyNodeBean a =(MyNodeBean) obj;
			return a.getAreaId().equals(this.getAreaId());
		}
		return false;
	}


}
