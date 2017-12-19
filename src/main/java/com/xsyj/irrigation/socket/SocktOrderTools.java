package com.xsyj.irrigation.socket;

public class SocktOrderTools {
	

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
		String high=str_x16.substring(2,4);
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










}
