package com.hub.shop.publicc.service;

import java.util.List;

import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.ShopData;
import com.hub.shop.common.model.ShopPointsAppliances;
import com.hub.shop.common.utils.CommonShopUtils;
import com.hub.shop.common.utils.ShopPointApplianceUtils;
import com.hub.shop.publicc.dao.DAOOperations;

/**
 * Service Class to access DAO.
 * 
 * This will be only entry to DAO class
 * 
 * @author Kiran
 *
 */
public class PublicCommonService {

	DAOOperations dao = new DAOOperations();

	public String getshopId(String shopName) {
		Log.logDebug(this, "IN getshopId()");
		return dao.getShopDetails(shopName).getShopId();
	}

	public List<String> getShopPoints(String shopId) {
		Log.logDebug(this, "IN getshopDevices()");
		return dao.getPoints(shopId).getPointIds();
	}

	public String getAppliance(String pointId) {
		Log.logDebug(this, "IN getAppliance()");
		return ShopPointApplianceUtils.getApplianceUsingPointId(pointId);
	}

	public void persistData(List<ShopData> publicDataList) {
		Log.logDebug(this, "persistData() <start>");
		for (ShopData shopData : publicDataList) {
			try {
				dao.persistData(shopData);
			} catch (Exception e) {
				Log.logFatal(this, "FATAL ERROR in persistData()", e);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.logError(this, "persistData()", e);
			} catch (Exception ex) {
				Log.logFatal(this, "FATAL ERROR in persistData()", ex);
			}
		}
		Log.logDebug(this, "persistData() <end>");
	}

	public ShopPointsAppliances getShopPointAppliances() {
		Log.logDebug(this, "getAllPointAppliances() <start>");
		String shopName = CommonShopUtils.getShopName();
		String shopId = getshopId(shopName);
		ShopPointsAppliances shopPointsAppliances = dao.getShopPointsAppliances(shopId);
		Log.logDebug(this, "getAllPointAppliances() <end>");
		return shopPointsAppliances;
	}
}
