package com.hub.shop.common.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to hold shop details...
 * 
 * @author Kiran
 *
 */
@Document(collection = "shop")
public class Shop {
	private String shopId;
	private String name;
	private String shopSize;
	private String address;
	private String pincode;
	private String contactNumber;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopSize() {
		return shopSize;
	}

	public void setShopSize(String shopSize) {
		this.shopSize = shopSize;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
