package com.hub.shop.publicc.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.utils.CommonShopUtils;
import com.hub.shop.common.utils.PropertyUtils;
import com.hub.shop.local.service.LocalMqttValidateAndPublish;

/**
 * 
 * @author Kiran
 *
 */
public class PublicMqttService implements MqttCallback {

	public PublicMqttService() {
		MqttAsyncClient publicClient = createPublicClient();
		createPublicSubscriber(publicClient);
	}

	/**
	 * 
	 * @return
	 */
	private MqttAsyncClient createPublicClient() {
		Log.logDebug(this, "createPublicClient() <start>");
		String brokerUrl = PropertyUtils.getPropertyValue(HubConstants.PUBLIC_BROKER_URL);
		String clientId = createPublicClientId();
		String dataStoreFilePath = PropertyUtils.getPropertyValue(HubConstants.PUBLIC_MQTT_DATASTORE_PATH);
		MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(dataStoreFilePath);
		MqttAsyncClient client = null;
		try {
			client = new MqttAsyncClient(brokerUrl, clientId, dataStore);
			Log.logInfo(this, "Public MQTT client creation <SUCCESS>");
		} catch (MqttException e) {
			Log.logError(this, "createPublicClient()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL ERROR in createPublicClient()", ex);
		}
		client.setCallback(this);
		Log.logDebug(this, "createPublicClient() <end>");
		return client;
	}

	/**
	 * 
	 * @return
	 */
	private String createPublicClientId() {
		Log.logDebug(this, "createPublicClientId() <start>");
		StringBuffer buffer = new StringBuffer(CommonShopUtils.getShopName());
		Log.logDebug(this, "createPublicClientId() <end>");
		return buffer.append("1").toString();
	}

	/**
	 * 
	 * @param publicClient
	 */
	private void createPublicSubscriber(MqttAsyncClient publicClient) {
		Log.logDebug(this, "createPublicSubscriber() <start>");
		boolean cleanSession = true;
		MqttConnectOptions conOptions = null;
		conOptions = new MqttConnectOptions();
		conOptions.setCleanSession(cleanSession);
		conOptions.setUserName(HubConstants.PUBLIC_MQTT_USER);
		conOptions.setUserName(HubConstants.PUBLIC_MQTT_PASS);
		IMqttToken conToken = null;
		try {
			conToken = publicClient.connect(conOptions, null, null);
			conToken.waitForCompletion();
			Log.logInfo(this, "Public MQTT client connection <SUCCESS>");
		} catch (MqttSecurityException e) {
			Log.logError(this, "createPublicSubscriber()", e);
		} catch (MqttException ex) {
			Log.logError(this, "createPublicSubscriber()", ex);
		} catch (Exception exc) {
			Log.logFatal(this, "FATAL ERROR in createPublicSubscriber()", exc);
		}
		IMqttToken subToken = null;
		try {
			subToken = publicClient.subscribe(CommonShopUtils.getShopName(), 0, null, null);
			subToken.waitForCompletion();
			Log.logInfo(this, "Public MQTT subscriptions <SUCCESS>");
		} catch (MqttException e) {
			Log.logError(this, "createPublicSubscriber()", e);
		} catch (Exception exc) {
			Log.logFatal(this, "FATAL ERROR in createPublicSubscriber()", exc);
		}
		Log.logDebug(this, "createPublicSubscriber() <end>");
	}

	/**
	 * 
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Log.logDebug(this, "messageArrived() <start>");
		Log.logInfo(this, "Message Received to Topic :: " + topic + " , Message :: " + message);
		new Thread() {
			public void run() {
				new LocalMqttValidateAndPublish(message);
			}
		}.start();
		Log.logDebug(this, "messageArrived() <end>");
	}

	/**
	 * 
	 */
	@Override
	public void connectionLost(Throwable cause) {
		Log.logError(this, "Connection Lost", cause);
	}

	/**
	 * 
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		Log.logDebug(this, "Delivery Complete");
	}
}