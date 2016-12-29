package com.hub.shop.local.service;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.utils.CommonShopUtils;
import com.hub.shop.common.utils.PropertyUtils;
import com.hub.shop.publicc.service.PublicCommonService;

/**
 * Service class to setup local mqtt connections.
 * 
 * @author Kiran
 *
 */
public class LocalShopService {

	PublicCommonService commonService = new PublicCommonService();

	public LocalShopService() {
		List<String> points = getShopPoints();
		setupSubscribers(points);
	}

	private List<String> getShopPoints() {
		Log.logDebug(this, "getShopDevices() <start>");
		String shopName = CommonShopUtils.getShopName();
		String shopId = commonService.getshopId(shopName);
		List<String> points = commonService.getShopPoints(shopId);
		Log.logDebug(this, "getShopDevices() <end>");
		return points;
	}

	private void setupSubscribers(List<String> points) {
		Log.logDebug(this, "IN setupSubscribers()");
		
		
		new LocalMqttService(points);
		new LocalMqttLWTSService();
	}

	
	/**
	 * Method to create local mqtt client
	 * 
	 * @return
	 */
	private MqttAsyncClient createLocalClient() {
		Log.logDebug(this, "createLocalClient() <start>");
		String brokerUrl = PropertyUtils.getPropertyValue(HubConstants.LOCAL_BROKER_URL);
		String clientId = HubConstants.LOCAL_MQTT_CLIENT_ID;
		String dataStoreFilePath = PropertyUtils.getPropertyValue(HubConstants.LOCAL_MQTT_DATASTORE_PATH);
		MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(dataStoreFilePath);
		MqttAsyncClient client = null;
		try {
			client = new MqttAsyncClient(brokerUrl, clientId, dataStore);
			Log.logInfo(this, "Local MQTT client creation <SUCCESS>");
		} catch (MqttException e) {
			Log.logError(this, "Mqtt Exception in createLocalClient()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in createLocalClient()", ex);
		}
		client.setCallback(this);
		Log.logDebug(this, "createLocalClient() <end>");
		return client;
	}
}