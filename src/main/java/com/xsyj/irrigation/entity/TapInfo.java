package com.xsyj.irrigation.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TapInfo implements Serializable{

	private int id;
    private String tapnum;
    private String tapname;
    private BigDecimal irrigationarea;
    private BigDecimal providevoltage;
    private BigDecimal cellvoltage;
    private BigDecimal pipelinepress;
    private String userid;
    private String taptype;
    private String tapbatch;
    private String netgatecode;
    private String netgatename;
    private String tapparam;
    private int pileid;
    private int pumpid;
    private String pileName;
    private String pumpName;
    private String userName;
    private int isBindA;
    private int isBindB;
    private String openMouth;
    private String stcd;
    private int isSelected; // 0 未选 ，1 选A，2 选B


    public TapInfo() {
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getStcd() {
		return stcd;
	}



	public void setStcd(String stcd) {
		this.stcd = stcd;
	}



	public int getIsBindA() {
		return isBindA;
	}
    
    

	public String getOpenMouth() {
		return openMouth;
	}



	public void setOpenMouth(String openMouth) {
		this.openMouth = openMouth;
	}



	public void setIsBindA(int isBindA) {
		this.isBindA = isBindA;
	}

	public int getIsBindB() {
		return isBindB;
	}

	public void setIsBindB(int isBindB) {
		this.isBindB = isBindB;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
   
    public String getPileName() {
		return pileName;
	}


	public void setPileName(String pileName) {
		this.pileName = pileName;
	}


	public String getPumpName() {
		return pumpName;
	}


	public void setPumpName(String pumpName) {
		this.pumpName = pumpName;
	}


	public int getId() {
        return id;
    }

   
    public void setId(int id) {
        this.id = id;
    }

   
    public String getTapnum() {
        return tapnum;
    }

  
    public void setTapnum(String tapnum) {
        this.tapnum = tapnum;
    }

   
    public String getTapname() {
        return tapname;
    }

   
    public void setTapname(String tapname) {
        this.tapname = tapname;
    }

   
    public BigDecimal getIrrigationarea() {
        return irrigationarea;
    }

   
    public void setIrrigationarea(BigDecimal irrigationarea) {
        this.irrigationarea = irrigationarea;
    }

   
    public BigDecimal getProvidevoltage() {
        return providevoltage;
    }

   
    public void setProvidevoltage(BigDecimal providevoltage) {
        this.providevoltage = providevoltage;
    }

  
    public BigDecimal getCellvoltage() {
        return cellvoltage;
    }

    
    public void setCellvoltage(BigDecimal cellvoltage) {
        this.cellvoltage = cellvoltage;
    }

   
    public BigDecimal getPipelinepress() {
        return pipelinepress;
    }

   
    public void setPipelinepress(BigDecimal pipelinepress) {
        this.pipelinepress = pipelinepress;
    }

   
    public String getUserid() {
        return userid;
    }

   
    public void setUserid(String userid) {
        this.userid = userid;
    }

   
    public String getTaptype() {
        return taptype;
    }

   
    public void setTaptype(String taptype) {
        this.taptype = taptype;
    }

   
    public String getTapbatch() {
        return tapbatch;
    }

   
    public void setTapbatch(String tapbatch) {
        this.tapbatch = tapbatch;
    }

  
    public String getNetgatecode() {
        return netgatecode;
    }

  
    public void setNetgatecode(String netgatecode) {
        this.netgatecode = netgatecode;
    }

   
    public String getNetgatename() {
        return netgatename;
    }

  
    public void setNetgatename(String netgatename) {
        this.netgatename = netgatename;
    }

   
    public String getTapparam() {
        return tapparam;
    }

   
    public void setTapparam(String tapparam) {
        this.tapparam = tapparam;
    }

  
    public int getPileid() {
        return pileid;
    }

   
    public void setPileid(int pileid) {
        this.pileid = pileid;
    }

   
    public int getPumpid() {
        return pumpid;
    }

  
    public void setPumpid(int pumpid) {
        this.pumpid = pumpid;
    }



	@Override
	public String toString() {
		return "TapInfo [id=" + id + ", tapnum=" + tapnum + ", tapname=" + tapname + ", irrigationarea="
				+ irrigationarea + ", providevoltage=" + providevoltage + ", cellvoltage=" + cellvoltage
				+ ", pipelinepress=" + pipelinepress + ", userid=" + userid + ", taptype=" + taptype + ", tapbatch="
				+ tapbatch + ", netgatecode=" + netgatecode + ", netgatename=" + netgatename + ", tapparam=" + tapparam
				+ ", pileid=" + pileid + ", pumpid=" + pumpid + ", pileName=" + pileName + ", pumpName=" + pumpName
				+ ", userName=" + userName + ", isBindA=" + isBindA + ", isBindB=" + isBindB + ", openMouth="
				+ openMouth + "]";
	}
    
    
}