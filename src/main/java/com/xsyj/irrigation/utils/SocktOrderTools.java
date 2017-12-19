package com.xsyj.irrigation.utils;


public class SocktOrderTools {
	

	
	/**
	 * 将9位网关编号，转换成16进制字符串，高位在前，低位在后，共 4个字节
	 * @param netGateCode 网关地址
	 * @param isChange  是否要高地位进行颠倒
	 * @return
	 */
	public static String getNetCode16(String netGateCode,boolean isChange) {
		Integer i_netcode=Integer.parseInt(netGateCode);
		String str_x16=String.valueOf(Integer.toHexString(i_netcode));
		if(i_netcode<=0x0fffffff && i_netcode>0x00ffffff){
			str_x16="0".concat(str_x16);
		}else if(i_netcode<=0x00ffffff && i_netcode>0x000fffff){
			str_x16="00".concat(str_x16);
		}else if(i_netcode<=0x000fffff && i_netcode>0x0000ffff){
			str_x16="000".concat(str_x16);
		}else if(i_netcode<=0x0000ffff && i_netcode>0x00000fff){
			str_x16="0000".concat(str_x16);
		}else if(i_netcode<=0x00000fff && i_netcode>0x000000ff){
			str_x16="00000".concat(str_x16);
		}else if(i_netcode<=0x000000ff && i_netcode>0x0000000f){
			str_x16="000000".concat(str_x16);
		}else if(i_netcode<=0x0000000f ){
			str_x16="0000000".concat(str_x16);
		}

		if(isChange){
			String low=str_x16.substring(0, 4);
			String high=str_x16.substring(4,8);
			str_x16=high.concat(low);
		}

		return str_x16;
	}

	/**
	 * 将数据长度转换成16进制字符串，高位在前，地位在后 ，共 2个字节
	 * @param data_len
	 * @return
	 */
	public static String getDataLen_16(Integer data_len) {
		 String str_x16=Integer.toHexString(data_len);
		if(data_len<=0x0fff && data_len>0x00ff){
			str_x16="0".concat(str_x16);
		}else if(data_len<=0x00ff && data_len>0x000f){
			str_x16="00".concat(str_x16);
		}else if(data_len<=0x000f){
			str_x16="000".concat(str_x16);
		}
		/*String low=str_x16.substring(0, 2);
		String high=str_x16.substring(2,4);
		str_x16=high.concat(low);*/
		return str_x16;
	}

	/**
	 * 返回时间的毫秒数long16进制，高位在前，地位在后 ,共4个字节
	 * @param time
	 * @return
	 */
	public static String getTime_16(long time) {
		if(time==0){
			return "00000000";
		}
		String str_x16=String.valueOf(Long.toHexString(time));
		if(time<=0x0fffffff && time>0x00ffffff){
			str_x16="0".concat(str_x16);
		}else if(time<=0x00ffffff && time>0x000fffff){
			str_x16="00".concat(str_x16);
		}else if(time<=0x000fffff && time>0x0000ffff){
			str_x16="000".concat(str_x16);
		}else if(time<=0x0000ffff && time>0x00000fff){
			str_x16="0000".concat(str_x16);
		}else if(time<=0x00000fff && time>0x000000ff){
			str_x16="00000".concat(str_x16);
		}else if(time<=0x000000ff && time>0x0000000f){
			str_x16="000000".concat(str_x16);
		}else if(time<=0x0000000f ){
			str_x16="0000000".concat(str_x16);
		}
	/*	String low=str_x16.substring(0, 4);
		String high=str_x16.substring(4,8);
		str_x16=high.concat(low);*/
		return str_x16;
	}

	/**
	 * 帧长度   一个字节
	 * @param frame_len 
	 * @return
	 */
	public static String getFramLen(Integer frame_len) {
		 String str_x16=Integer.toHexString(frame_len);
			if(frame_len<=0x0f ){
				str_x16="0".concat(str_x16);
			}
			return str_x16;
	}

	/**
	 * 获取阀门控制命令16进制字节字符串 ，共三个字节，《 3个字节一个设备（地址2 + 控阀命令码1》
	 * @param tapNum
	 * @param str_size   从tapNum 最后截取str_size 个字符串
	 * @param whichMouth
	 * @param dowhat         0:关 1：开，10 复位
	 * @return
	 */
	public static String getTapControlOrder(String tapNum, int str_size, String whichMouth, int dowhat) {
		Integer tap_int=Integer.parseInt(tapNum.substring(tapNum.length() - str_size, tapNum.length()));
		//System.out.println("阀门后5位："+tapNum.substring(tapNum.length()-str_size,tapNum.length()));
		String tap_str16=getDataLen_16(tap_int);
		String control_str16="";
		switch (dowhat) {
		case 0:
			control_str16=control_str16.concat("02");
			break;
        case 1:
			   if("A".equals(whichMouth)){
				   control_str16=control_str16.concat("02");
			   }else{
				   control_str16=control_str16.concat("03");
			   }
				   
			break;
        case 10:
        	control_str16=control_str16.concat("0A");
	        break;

		}
		
		return tap_str16.concat(control_str16);
	}
	
	
	/**
	 * CRC高地位转换 ，共 2个字节
	 * @param data_len
	 * @return
	 */
	public static String getCRC_16(Integer data_len) {
		 String str_x16=Integer.toHexString(data_len);
		if(data_len<=0x0fff && data_len>0x00ff){
			str_x16="0".concat(str_x16);
		}else if(data_len<=0x00ff && data_len>0x000f){
			str_x16="00".concat(str_x16);
		}else if(data_len<=0x000f){
			str_x16="000".concat(str_x16);
		}
		String low=str_x16.substring(0, 2);
		String high=str_x16.substring(2, 4);
		str_x16=high.concat(low);
		return str_x16;
	}



	/**
	 * 获取阀门控制命令16进制字节字符串 ，共四个字节，《设备编号》
	 * 安卓
	 * @param tapNum
	 * @param str_size   从tapNum 最后截取str_size 个字符串
	 * @return
	 */
	public static String getTapControlOrderToFour(String tapNum, int str_size) {
		//截取后五位
		Integer tap_after=Integer.parseInt(tapNum.substring(tapNum.length()-str_size,tapNum.length()));
		//截取前4位
		Integer tap_before=Integer.parseInt(tapNum.substring(0, tapNum.length() - str_size));
		String tap_after16=getDataLen_16(tap_after);
		String tap_before16=getDataLen_16(tap_before);
		return tap_after16+tap_before16;
	}









}
