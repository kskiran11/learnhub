package com.hub.shop.publicc.utils;

import com.google.gson.Gson;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.ShopData;

/**
 * 
 * @author Kiran
 *
 */
public class PublicJsonUtils {
	private PublicJsonUtils() {

	}

	/**
	 * Method to convert (public)data to json
	 * 
	 * @param shopData
	 * @return
	 */
	public static String convertToJson(ShopData shopData) {
		Log.logDebug("PublicJsonUtils", "convertToJson() <start>");
		String jsonString = "";
		Gson gson = new Gson();
		try {
			jsonString = gson.toJson(shopData);
			Log.logInfo("PublicJsonUtils", "Public Json Conversion <SUCCESS>");
		} catch (Exception e) {
			Log.logFatal("PublicJsonUtils", "Fatal Exception in convertToJson()", e);
		}
		Log.logDebug("PublicJsonUtils", "convertToJson() <end>");
		return jsonString;
	}
}
