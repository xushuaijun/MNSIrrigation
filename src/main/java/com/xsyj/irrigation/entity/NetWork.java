package com.xsyj.irrigation.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xsyj.irrigation.NetAllActivity;
import com.xsyj.irrigation.NetWorkActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;
import com.xsyj.irrigation.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/12/22.
 */
public class NetWork extends AdapterGetView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String netWorkName;  //代表了：网关编号，轮灌组，阀门编号
    private String state;
    private String netGateCode;
    private String pileName;
    private List<NetWork> children;
    private List<NetWork> works = new ArrayList<>();

    public NetWork() {
        super();
    }

    public NetWork(int id, String netWorkName, String state, String netGateCode, List<NetWork> children, String pileName) {
        this.id = id;
        this.netWorkName = netWorkName;
        this.state = state;
        this.netGateCode = netGateCode;
        this.children = children;
        this.pileName = pileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetWorkName() {
        return netWorkName;
    }

    public void setNetWorkName(String netWorkName) {
        this.netWorkName = netWorkName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNetGateCode() {
        return netGateCode;
    }

    public void setNetGateCode(String netGateCode) {
        this.netGateCode = netGateCode;
    }

    public String getPileName() {
        return pileName;
    }

    public void setPileName(String pileName) {
        this.pileName = pileName;
    }

    public List<NetWork> getChildren() {
        return children;
    }

    public void setChildren(List<NetWork> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "NetWork{" +
                "id=" + id +
                ", netWorkName='" + netWorkName + '\'' +
                ", state='" + state + '\'' +
                ", netGateCode='" + netGateCode + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
        return null;
    }

    @Override
    public View getView(Object object, int position, View convertView, LayoutInflater inflater, final Context context) {
        ViewHolder vh;
        if(convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.netall_item, null);
            vh.netall_cb = (CheckBox) convertView.findViewById(R.id.netall_cb);
            vh.netall_title = (TextView) convertView.findViewById(R.id.netall_title);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final NetWork nw = (NetWork) object;

        vh.netall_title.setText(nw.getNetWorkName());
        vh.netall_cb.setOnCheckedChangeListener(  new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String netgetcode = nw.getNetWorkName();
                Map<String, IrrigationTask> map = ((NetAllActivity) context).getTasks();
                if(isChecked) {
                    IrrigationTask task = new IrrigationTask();

                    task.setNetGateCode(netgetcode);
                    task.setTapnum("");
                    task.setOpenmouth("");
                    task.setStime("");
                    task.setIrrigationtime(0);
                    task.setIrrigationwater(0);
                    task.setDowhat(1);
                    task.setIrrigationtype(14);
                    task.setState(-1);
                    map.put(netgetcode, task);
                }else{
                    if(map.get(netgetcode)!=null){
                        map.remove(netgetcode);
                    }
                }
            }
        });
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

        public CheckBox netall_cb;
        public TextView netall_title;
    }
}
