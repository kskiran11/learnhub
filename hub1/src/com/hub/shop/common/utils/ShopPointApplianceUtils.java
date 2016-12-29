package com.hub.shop.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.PointsAppliances;
import com.hub.shop.common.model.ShopPointsAppliances;
import com.hub.shop.publicc.service.PublicCommonService;

/**
 * Utils method to hold mapping between appliances and points
 * 
 * @author Kiran
 *
 */
public class ShopPointApplianceUtils {

	private static Map<String, String> pointIdApplianceMap = null;

	private ShopPointApplianceUtils() {

	}

	/**
	 * 
	 * @return
	 */
	private static Map<String, String> getShopPointAppliancesMap() {
		Log.logDebug("ShopPointApplianceUtils", "getShopPointAppliancesMap() <start>");
		pointIdApplianceMap = new HashMap<String, String>();
		pointIdApplianceMap = createShopPointApplianceMap(new PublicCommonService().getShopPointAppliances());
		Log.logDebug("ShopPointApplianceUtils", "getShopPointAppliancesMap() <end>");
		return pointIdApplianceMap;
	}

	/**
	 * 
	 * @param shopPointsAppliances
	 * @return
	 */
	private static Map<String, String> createShopPointApplianceMap(ShopPointsAppliances shopPointsAppliances) {
		Log.logDebug("ShopPointApplianceUtils", "createShopPointApplianceMap() <start>");
		Map<String, String> applianceMap = new HashMap<String, String>();
		for (PointsAppliances pointAppliance : shopPointsAppliances.getPointsAppliances()) {
			applianceMap.put(pointAppliance.getPointId(), pointAppliance.getAppliance());
		}
		Log.logDebug("ShopPointApplianceUtils", "createShopPointApplianceMap() <end>");
		return applianceMap;
	}

	/**
	 * Method to get Appliance name for particular Point Id
	 * 
	 * @param pointId
	 * @return appliance
	 */
	public static String getApplianceUsingPointId(String pointId) {
		Log.logDebug("ShopPointApplianceUtils", "IN getAppliance()");
		if (pointIdApplianceMap == null) {
			getShopPointAppliancesMap();
		}
		for (Map.Entry<String, String> entry : pointIdApplianceMap.entrySet()) {
			if (StringUtils.equals(pointId, entry.getKey())) {
				return entry.getValue();
			}
		}
		Log.logInfo("ShopPointApplianceUtils", "Appliance Not found for ID :: " + pointId);
		Log.logFatal("ShopPointApplianceUtils", "<CHECK> MAPPING for ID :: " + pointId, null);
		return "ERROR";
	}

	/**
	 * Method to get Point Id name for particular Appliance
	 * 
	 * @param appliance
	 * @return pointId
	 */
	public static String getPointIdUsingAppliance(String appliance) {
		Log.logDebug("ShopPointApplianceUtils", "IN getAppliance()");
		if (pointIdApplianceMap == null) {
			getShopPointAppliancesMap();
		}
		for (Map.Entry<String, String> entry : pointIdApplianceMap.entrySet()) {
			if (StringUtils.equals(appliance, entry.getValue())) {
				return entry.getKey();
			}
		}
		Log.logInfo("ShopPointApplianceUtils", "PointId Not found for Appliance :: " + appliance);
		Log.logFatal("ShopPointApplianceUtils", "<CHECK> MAPPING for Appliance :: " + appliance, null);
		return HubConstants.ERROR;
	}
}
