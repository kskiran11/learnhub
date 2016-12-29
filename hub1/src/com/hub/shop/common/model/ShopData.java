package com.hub.shop.common.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to hold shop data. Holds details <Shop> and <ShopData>
 * 
 * This is the final output of hub
 * 
 * @author Kiran
 *
 */
@Document(collection = "shopdata")
public class ShopData {

	private String date;
	private String shopName;
	private String appliance;
	private List<ShopApplianceData> data;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAppliance() {
		return appliance;
	}

	public void setAppliance(String appliance) {
		this.appliance = appliance;
	}

	public List<ShopApplianceData> getData() {
		return data;
	}

	public void setData(List<ShopApplianceData> data) {
		this.data = data;
	}

}
