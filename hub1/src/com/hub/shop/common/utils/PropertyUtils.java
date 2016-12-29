package com.hub.shop.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;

/**
 * Property Utils Class. Class reads app properties file
 * 
 * @author Kiran
 *
 */
public class PropertyUtils {

	/** Properties instance */
	private static Properties properties;

	/**
	 * Private Constructor. Singleton class.
	 */
	private PropertyUtils() {
	}

	/**
	 * Method to load properties from app properties file
	 * 
	 * @return
	 */
	private static Properties getProperties() {
		properties = new Properties();
		String resourceName = HubConstants.APP_PROPERTIES;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			properties.load(resourceStream);
		} catch (IOException e) {
			Log.logError("PropertyUtils", "IO Exception in getProperties()", e);
		} catch (Exception ex) {
			Log.logFatal("PropertyUtils", "FATAL EXCEPTION in getProperties()", ex);
		}
		return properties;
	}

	/**
	 * Method to get property key value. Key is passed as argument
	 * 
	 * @param key
	 * @return value
	 */
	public static String getPropertyValue(String key) {
		Log.logDebug("PropertyUtils", "Fetching property value for key :: " + key);
		if (properties == null) {
			properties = new Properties();
			properties = getProperties();
			return properties.getProperty(key);
		} else {
			return properties.getProperty(key);
		}
	}
}