package com.hub.shop.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;

/**
 * 
 * @author Kiran
 *
 */
public class CommonShopUtils {

	private CommonShopUtils() {

	}

	private static String shopName;

	/**
	 * Method to get shop name from properties file
	 * 
	 * @return
	 */
	public static String getShopName() {
		Log.logDebug("CommonShopUtils", "getShopName() <start>");
		if (StringUtils.isBlank(shopName) || StringUtils.isEmpty(shopName)) {
			Properties properties = new Properties();
			String file = PropertyUtils.getPropertyValue(HubConstants.SHOP_NAME_PATH) + HubConstants.SHOP_FILE_NAME;
			try {
				properties.load(new FileInputStream(file));
			} catch (IOException e) {
				Log.logError("CommonShopUtils", "IO Exception in getShopName()", e);
			} catch (Exception ex) {
				Log.logFatal("CommonShopUtils", "FATAL EXCEPTION in getShopName()", ex);
			}
			shopName = properties.getProperty(HubConstants.SHOP_NAME);
		}
		Log.logInfo("CommonShopUtils", "Returning Shop Name :: " + shopName);
		Log.logDebug("CommonShopUtils", "getShopName() <end>");
		return shopName;
	}
}
