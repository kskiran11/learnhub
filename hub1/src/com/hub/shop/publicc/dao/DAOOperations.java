package com.hub.shop.publicc.dao;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.Points;
import com.hub.shop.common.model.Shop;
import com.hub.shop.common.model.ShopData;
import com.hub.shop.common.model.ShopPointsAppliances;
import com.hub.shop.publicc.utils.DBUtils;

/**
 * Class that has all DAO Operations.
 * 
 * Call this class for any DAO operations.
 * 
 * @author Kiran
 *
 */
public class DAOOperations {
	/**
	 * 
	 * @param shopName
	 * @return
	 */
	public Shop getShopDetails(String shopName) {
		Log.logDebug(this, "getShopDetails() <Start>");
		Query query = new Query();
		query.addCriteria(Criteria.where(HubConstants.NAME).is(shopName));
		Shop shop = null;
		try {
			shop = getDBConnection().findOne(query, Shop.class);
		} catch (Exception e) {
			Log.logFatal(this, "FATAL Exception in getShopDetails()", e);
		}
		Log.logDebug(this, "getShopDetails() <end>");
		return shop;
	}

	public Points getPoints(String shopId) {
		Log.logDebug(this, "getPoints() <Start>");
		Query query = new Query();
		query.addCriteria(Criteria.where(HubConstants.SHOP_ID).is(shopId));
		Points points = null;
		try {
			points = getDBConnection().findOne(query, Points.class);
		} catch (Exception e) {
			Log.logFatal(this, "FATAL Exception in getPoints()", e);
		}
		Log.logDebug(this, "getPoints() <end>");
		return points;
	}

	public ShopPointsAppliances getShopPointsAppliances(String shopId) {
		Log.logDebug(this, "getShopPointsAppliances() <start>");
		Query query = new Query();
		query.addCriteria(Criteria.where(HubConstants.SHOP_ID).is(shopId));
		ShopPointsAppliances shopPointsAppliances = null;
		try {
			shopPointsAppliances = getDBConnection().findOne(query, ShopPointsAppliances.class);
		} catch (Exception e) {
			Log.logFatal(this, "FATAL Exception in getShopPointsAppliances()", e);
		}
		Log.logDebug(this, "getShopPointsAppliances() <end>");
		return shopPointsAppliances;
	}

	public void persistData(ShopData shopData) {
		Log.logDebug(this, "IN persistData()");
		try {
			getDBConnection().insert(shopData);
		} catch (Exception e) {
			Log.logFatal(this, "FATAL Exception in persistData()", e);
		}
	}

	private MongoOperations getDBConnection() {
		Log.logDebug(this, "IN getDBConnection()");
		return DBUtils.getMongoOperationClient();
	}
}
