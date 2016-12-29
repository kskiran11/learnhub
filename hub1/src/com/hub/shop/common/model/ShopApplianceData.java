package com.hub.shop.common.model;

import org.apache.commons.lang.StringUtils;

import com.hub.shop.common.constants.HubConstants;

/**
 * Class to hold Appliance Data
 * 
 * @author Kiran
 *
 */
public class ShopApplianceData {

	private String time;
	private String current;
	private String state;

	public ShopApplianceData() {
	}

	public ShopApplianceData(String time, String current, String state) {
		this.time = time;
		this.current = current;
		setState(state);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (StringUtils.equals(state, "1")) {
			this.state = HubConstants.ON;
		} else if (StringUtils.equals(state, "0")) {
			this.state = HubConstants.OFF;
		} else {
			this.state = HubConstants.STATE_NA;
		}
	}
}
