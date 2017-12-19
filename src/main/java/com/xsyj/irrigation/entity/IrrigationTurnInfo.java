package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;
import java.util.Date;

public class IrrigationTurnInfo extends AdapterGetView implements Serializable {

    /**
     * 轮灌组监控
     */
    private static final long serialVersionUID = 1L;

    private Integer id;               //轮灌组id
    private String turnname;          //轮灌组名称
    private long addtime;             //添加时间
    private String userid;            //用户id
    private String pileName;          //出地桩-阀口
    private String openMouth;         //轮灌组对应这个阀门的哪个口，A或B
    private Double totalWater;        //总用水量
    private Double totalTime;         //总灌溉时间
    private int tapId;                //阀门id
    private String tapNum;            //阀门编号
    private int pileId;               //出地桩id
    private int aMounth;              //A口状态
    private int bMounth;              //B口状态
    private long addTime;             //谁知道什么添加时间
    private Long nextExeTime;         //下一次执行时间
    private int startType;            //启动类型
    private int turnState;            //轮灌组状态
    private String etTimes;           //执行、总次数
    private String tapIds;
    private String turntapIds;
    private int mouthCount;           //阀口数
    private double irrigationArea;    // 阀门灌溉面积
    private double irrigationAreas;   // 对应的轮灌面积
    private String areaId;
    private String areaLevel;
    private int ids;

    private int flag;                //轮灌组删除标识

    private String name;             //获取（tapid\对应出水口）
    private String img;
    private int turnId;
    private int irrigationWater;
    private String startTime;
    private String endTime;
    private String start_id;
    private String page_size;

    private boolean isSelected;      // 条件筛选时 是否被选中


    public IrrigationTurnInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public IrrigationTurnInfo(Integer id, String turnname, long addtime, String userid, String pileName, String openMouth, Double totalWater, Double totalTime, int tapId, String tapNum, int pileId, int aMounth, int bMounth, long addTime, Long nextExeTime, int startType, int turnState, String etTimes, String tapIds, String turntapIds, int mouthCount, double irrigationArea, double irrigationAreas, int ids, int flag, String name, boolean isSelected) {
        this.id = id;
        this.turnname = turnname;
        this.addtime = addtime;
        this.userid = userid;
        this.pileName = pileName;
        this.openMouth = openMouth;
        this.totalWater = totalWater;
        this.totalTime = totalTime;
        this.tapId = tapId;
        this.tapNum = tapNum;
        this.pileId = pileId;
        this.aMounth = aMounth;
        this.bMounth = bMounth;
        this.addTime = addTime;
        this.nextExeTime = nextExeTime;
        this.startType = startType;
        this.turnState = turnState;
        this.etTimes = etTimes;
        this.tapIds = tapIds;
        this.turntapIds = turntapIds;
        this.mouthCount = mouthCount;
        this.irrigationArea = irrigationArea;
        this.irrigationAreas = irrigationAreas;
        this.ids = ids;
        this.flag = flag;
        this.name = name;
        this.isSelected = isSelected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTurnname() {
        return turnname;
    }

    public void setTurnname(String turnname) {
        this.turnname = turnname;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPileName() {
        return pileName;
    }

    public void setPileName(String pileName) {
        this.pileName = pileName;
    }

    public String getOpenMouth() {
        return openMouth;
    }

    public void setOpenMouth(String openMouth) {
        this.openMouth = openMouth;
    }

    public Double getTotalWater() {
        return totalWater;
    }

    public void setTotalWater(Double totalWater) {
        this.totalWater = totalWater;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public int getTapId() {
        return tapId;
    }

    public void setTapId(int tapId) {
        this.tapId = tapId;
    }

    public String getTapNum() {
        return tapNum;
    }

    public void setTapNum(String tapNum) {
        this.tapNum = tapNum;
    }

    public int getPileId() {
        return pileId;
    }

    public void setPileId(int pileId) {
        this.pileId = pileId;
    }

    public int getaMounth() {
        return aMounth;
    }

    public void setaMounth(int aMounth) {
        this.aMounth = aMounth;
    }

    public int getbMounth() {
        return bMounth;
    }

    public void setbMounth(int bMounth) {
        this.bMounth = bMounth;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public Long getNextExeTime() {
        return nextExeTime;
    }

    public void setNextExeTime(Long nextExeTime) {
        this.nextExeTime = nextExeTime;
    }

    public int getStartType() {
        return startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }

    public int getTurnState() {
        return turnState;
    }

    public void setTurnState(int turnState) {
        this.turnState = turnState;
    }

    public String getEtTimes() {
        return etTimes;
    }

    public void setEtTimes(String etTimes) {
        this.etTimes = etTimes;
    }

    public String getTapIds() {
        return tapIds;
    }

    public void setTapIds(String tapIds) {
        this.tapIds = tapIds;
    }

    public String getTurntapIds() {
        return turntapIds;
    }

    public void setTurntapIds(String turntapIds) {
        this.turntapIds = turntapIds;
    }

    public int getMouthCount() {
        return mouthCount;
    }

    public void setMouthCount(int mouthCount) {
        this.mouthCount = mouthCount;
    }

    public double getIrrigationArea() {
        return irrigationArea;
    }

    public void setIrrigationArea(double irrigationArea) {
        this.irrigationArea = irrigationArea;
    }

    public double getIrrigationAreas() {
        return irrigationAreas;
    }

    public void setIrrigationAreas(double irrigationAreas) {
        this.irrigationAreas = irrigationAreas;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public int getIrrigationWater() {
        return irrigationWater;
    }

    public void setIrrigationWater(int irrigationWater) {
        this.irrigationWater = irrigationWater;
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

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "IrrigationTurnInfo{" +
                "id=" + id +
                ", turnname='" + turnname + '\'' +
                ", addtime=" + addtime +
                ", userid='" + userid + '\'' +
                ", pileName='" + pileName + '\'' +
                ", openMouth='" + openMouth + '\'' +
                ", totalWater=" + totalWater +
                ", totalTime=" + totalTime +
                ", tapId=" + tapId +
                ", tapNum='" + tapNum + '\'' +
                ", pileId=" + pileId +
                ", aMounth=" + aMounth +
                ", bMounth=" + bMounth +
                ", addTime=" + addTime +
                ", nextExeTime=" + nextExeTime +
                ", startType=" + startType +
                ", turnState=" + turnState +
                ", etTimes='" + etTimes + '\'' +
                ", tapIds='" + tapIds + '\'' +
                ", turntapIds='" + turntapIds + '\'' +
                ", mouthCount=" + mouthCount +
                ", irrigationArea=" + irrigationArea +
                ", irrigationAreas=" + irrigationAreas +
                ", ids=" + ids +
                ", flag=" + flag +
                ", name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditem, null);
            vh.btn_lgz = (TextView) convertView.findViewById(R.id.btn_item);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final IrrigationTurnInfo it = (IrrigationTurnInfo) object;
        vh.btn_lgz.setText(it.getTurnname());
        vh.btn_lgz.setSelected(it.isSelected);
        return convertView;
    }

    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, final Context context) {

        return convertView;
    }

    @Override
    public View getView1(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    public static class ViewHolder {
        TextView btn_lgz;
    }


}