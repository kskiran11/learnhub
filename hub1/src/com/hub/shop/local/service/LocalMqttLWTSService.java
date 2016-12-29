package com.hub.shop.local.service;

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
import com.hub.shop.common.utils.PropertyUtils;

/**
 * Local MQTT Service.
 * 
 * @author Kiran
 *
 */
public class LocalMqttLWTSService implements MqttCallback {

	public LocalMqttLWTSService() {
		MqttAsyncClient localClient = createLocalClient();
		createLocalLwtSubscriber(localClient);
	}

	/**x	
	 * Method to create local mqtt client
	 * 
	 * @return
	 */
	private MqttAsyncClient createLocalClient() {
		Log.logDebug(this, "createLocalClient() <start>");
		String brokerUrl = PropertyUtils.getPropertyValue(HubConstants.LOCAL_BROKER_URL);
		String clientId = HubConstants.LOCAL_MQTT_LWT_CLIENT_ID;
		String dataStoreFilePath = PropertyUtils.getPropertyValue(HubConstants.LOCAL_MQTT_LWT_DATASTORE_PATH);
		MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(dataStoreFilePath);
		MqttAsyncClient client = null;
		try {
			client = new MqttAsyncClient(brokerUrl, clientId, dataStore);
			Log.logInfo(this, "Local LWTS client creation <SUCCESS>");
		} catch (MqttException e) {
			Log.logError(this, "Mqtt Exception in createLocalClient()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in createLocalClient()", ex);
		}
		client.setCallback(this);
		Log.logDebug(this, "createLocalClient() <end>");
		return client;
	}

	/**
	 * 
	 * @param localClient
	 */
	private void createLocalLwtSubscriber(MqttAsyncClient localClient) {
		Log.logDebug(this, "createLocalLwtSubscriber() <start>");
		boolean cleanSession = true;
		MqttConnectOptions conOptions = null;
		conOptions = new MqttConnectOptions();
		conOptions.setCleanSession(cleanSession);
		IMqttToken conToken = null;
		try {
			conToken = localClient.connect(conOptions, null, null);
			conToken.waitForCompletion();
			Log.logInfo(this, "Local LWTS client connection <SUCCESS>");
		} catch (MqttSecurityException e) {
			Log.logError(this, "Mqtt Security Exception in createLocalLwtSubscriber()", e);
		} catch (MqttException ex) {
			Log.logError(this, "Mqtt Exception in createLocalLwtSubscriber()", ex);
		} catch (Exception exc) {
			Log.logFatal(this, "FATAL EXCEPTION in createLocalLwtSubscriber()", exc);
		}
		try {
			IMqttToken subToken = null;
			subToken = localClient.subscribe(HubConstants.LOCAL_MQTT_LWT_TOPIC, 0, null, null);
			subToken.waitForCompletion();
			Log.logInfo(this, "Local LWTS subscription <SUCCESS>");
		} catch (MqttException e) {
			Log.logError(this, "Mqtt Exception in createLocalLwtSubscriber()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in createLocalLwtSubscriber()", ex);
		}
		Log.logDebug(this, "createLocalLwtSubscriber() <end>");
	}

	/**
	 * 
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Log.logDebug(this, "IN messageArrived()");
		Log.logInfo(this, "LWTS Received : Point disconnected :: " + topic);
	}

	/**
	 * 
	 */
	@Override
	public void connectionLost(Throwable cause) {
		Log.logDebug(this, "Connection Lost");
		Log.logError(this, "Connection Lost connectionLost()", cause);
	}

	/**
	 * 
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		Log.logDebug(this, "Delivery Complete");
	}
}
