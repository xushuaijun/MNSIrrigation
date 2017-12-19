package com.xsyj.irrigation.utils;


import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xsyj.irrigation.JsonDateAdapter.DateAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonParserUtil {

	/**
	 * 获取对象
	 * @param jsonstr
	 * @param type
	 * @return
	 */
	public static<T> T getObject(String jsonstr,Type type){
		Log.e("JsonParserUtil", "进入json解析");
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonstr, type);
		} catch (Exception e) {
			Log.e("JsonParserUtil", "json解析报错"+e.getMessage());
			e.printStackTrace();
		}
		return t;

	}


	/**
	 * 转换成Date类型
	 * @param jsonstr
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static<T> T getDateObject(String jsonstr,Type type){
		Log.e("JsonParserUtil", "进入json解析");
		T t = null;
		try {
			Gson gson = null;
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Date.class, new DateAdapter());
			builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
			gson = builder.create();
			t = gson.fromJson(jsonstr, type);
		} catch (Exception e) {
			Log.e("JsonParserUtil", "json解析报错");
			e.printStackTrace();
		}
		return t;

	}



	/**
	 * 获取对象
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public static<T> T getObject(String jsonstr,Class<T> cls){

		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonstr, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;

	}

	/**
	 * 获取对象集合
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {}.getType());
		} catch (Exception e) {
		}
		return list;
	}







}