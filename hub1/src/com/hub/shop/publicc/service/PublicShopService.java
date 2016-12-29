package com.hub.shop.publicc.service;

import com.hub.shop.common.log.Log;

/**
 * 
 * @author Kiran
 *
 */
public class PublicShopService {

	public PublicShopService() {
		setupPublicSubscriber();
	}

	/**
	 * 
	 */
	private void setupPublicSubscriber() {
		Log.logDebug(this, "IN setupPublicSubscriber()");
		new PublicMqttService();
	}
}