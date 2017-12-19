package com.xsyj.irrigation.BlueTooth;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xsyj.irrigation.R;
import com.xsyj.irrigation.adapter.AdapterGetView;

import java.io.Serializable;

public class MyBTDevice extends AdapterGetView implements Serializable {
	private BluetoothDevice btd;
	private String btd_state;
	
	public MyBTDevice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyBTDevice(BluetoothDevice btd, String btd_state) {
		super();
		this.btd = btd;
		this.btd_state = btd_state;
	}
	
	
	public BluetoothDevice getBtd() {
		return btd;
	}
	public void setBtd(BluetoothDevice btd) {
		this.btd = btd;
	}
	public String getBtd_state() {
		return btd_state;
	}
	public void setBtd_state(String btd_state) {
		this.btd_state = btd_state;
	}


	public boolean equals(MyBTDevice o) {

		return this.btd.getName().equals(o.getBtd().getName());
	}

	public int hashCode() {
		return this.btd.getName().hashCode();

	}


	@Override
	public View getStringView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
		ViewHolder vh;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=inflater.inflate(R.layout.btditem_xml, null);
			vh.tv_btddevice=(TextView)convertView.findViewById(R.id.text1);
			vh.tv_devicestate=(TextView)convertView.findViewById(R.id.text2);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder)convertView.getTag();
		}
		vh.tv_btddevice.setTextColor(context.getResources().getColor(android.R.color.black));
//		vh.tv_btddevice.setTextSize(context.getResources().getDimension(R.dimen.sp_8));
		vh.tv_devicestate.setVisibility(View.VISIBLE);

		if(object!=null){

			String classtype=object.getClass().getSimpleName();
			if("String".equals(classtype)){
				vh.tv_btddevice.setText(String.valueOf(object));
				vh.tv_btddevice.setTextColor(context.getResources().getColor(R.color.lightblue));
//				vh.tv_btddevice.setTextSize(context.getResources().getDimension(R.dimen.sp_10));
				vh.tv_devicestate.setVisibility(View.GONE);
			}else if("MyBTDevice".equals(classtype)){
				vh.tv_btddevice.setText(((MyBTDevice)object).getBtd().getName());
				vh.tv_devicestate.setText(((MyBTDevice)object).getBtd_state());
			}

		}

		return convertView;
	}


	static class ViewHolder{
		TextView tv_btddevice;
		TextView tv_devicestate;
	}

	@Override
	public View getView(Object object, int position, View convertView, LayoutInflater inflater, Context context) {
		return null;
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
}
