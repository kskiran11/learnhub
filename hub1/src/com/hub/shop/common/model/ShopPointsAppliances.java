package com.hub.shop.common.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to map shop's points to appliances
 * 
 * @author Kiran
 *
 */
@Document(collection = "shoppointsappliancesmapping1")
public class ShopPointsAppliances {

	private String shopId;
	private List<PointsAppliances> pointsAppliances;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public List<PointsAppliances> getPointsAppliances() {
		return pointsAppliances;
	}

	public void setPointsAppliances(List<PointsAppliances> pointsAppliances) {
		this.pointsAppliances = pointsAppliances;
	}
}
