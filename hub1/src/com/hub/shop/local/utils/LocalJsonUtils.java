package com.hub.shop.local.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.PointData;

/**
 * Local Json Utils
 * 
 * @author Kiran
 *
 */
public class LocalJsonUtils {

	private LocalJsonUtils() {

	}

	/**
	 * Method to convert data to json
	 * 
	 * @param pointData
	 * @return
	 */
	public static String convertToJson(List<PointData> pointData) {
		Log.logDebug("LocalJsonUtils", "convertToJson() <start>");
		String jsonString = "";
		Gson gson = new Gson();
		try {
			jsonString = gson.toJson(pointData);
			Log.logInfo("LocalJsonUtils", "Local Json Conversion <SUCCESS>");
		} catch (Exception e) {
			Log.logFatal("LocalJsonUtils", "Fatal Exception in convertFromJson()", e);
		}
		Log.logDebug("LocalJsonUtils", "convertToJson() <end>");
		return jsonString;
	}

	/**
	 * Method to convert json to data
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<PointData> convertFromJson(String jsonString) {
		Log.logDebug("LocalJsonUtils", "convertFromJson() <start>");
		List<PointData> pointData = new ArrayList<PointData>();
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<PointData>>() {
		}.getType();
		try {
			pointData = gson.fromJson(jsonString, collectionType);
			Log.logInfo("LocalJsonUtils", "Local Json Parse <SUCCESS>");
		} catch (Exception e) {
			Log.logFatal("LocalJsonUtils", "Fatal Exception in convertFromJson()", e);
		}
		Log.logDebug("LocalJsonUtils", "convertFromJson() <end>");
		return pointData;
	}
}
