package com.hub.shop.common.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to hold Device ids. Relation :: for a particular shop<shopId> multiple
 * points will be availible <pointId>
 * 
 * @author Kiran
 *
 */
@Document(collection = "points")
public class Points {

	private String shopId;

	/** Variable to hold device list. */
	List<String> pointIds = new ArrayList<String>();

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public List<String> getPointIds() {
		return pointIds;
	}

	public void setPointIds(List<String> pointIds) {
		this.pointIds = pointIds;
	}

}
