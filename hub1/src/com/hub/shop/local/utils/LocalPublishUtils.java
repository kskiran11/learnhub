package com.hub.shop.local.utils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.utils.PropertyUtils;

/**
 * Local Publish Utils
 * 
 * @author Kiran
 *
 */
public class LocalPublishUtils {

	private LocalPublishUtils() {
	}

	/**
	 * Create Local Mqtt Client
	 * 
	 * @return
	 */
	private static MqttClient createLocalClient() {
		Log.logDebug("LocalPublishUtils", "createLocalClient() <start>");
		String brokerUrl = PropertyUtils.getPropertyValue(HubConstants.LOCAL_BROKER_URL);
		String clientId = HubConstants.LOCAL_MQTT_PUBLISH_CLIENT_ID;
		String dataStoreFilePath = PropertyUtils.getPropertyValue(HubConstants.LOCAL_PUBLISH_MQTT_DATASTORE_PATH);
		MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(dataStoreFilePath);
		MqttClient client = null;
		try {
			client = new MqttClient(brokerUrl, clientId, dataStore);
			Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client creation <SUCCESS>");
		} catch (MqttException e) {
			Log.logError("LocalPublishUtils", "convertFromJson()", e);
		} catch (Exception ex) {
			Log.logFatal("LocalPublishUtils", "FATAL ERROR in convertFromJson()", ex);
		}
		Log.logDebug("LocalPublishUtils", "createLocalClient() <end>");
		return client;
	}

	/**
	 * Method to publish mqtt message
	 * 
	 * @param topicName
	 * @param qos
	 * @param payload
	 * @throws MqttException
	 */
	private static void publish(String topicName, byte[] payload, MqttClient client) throws MqttException {
		Log.logDebug("LocalPublishUtils", "publish() <start>");
		MqttConnectOptions conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(true);
		client.connect(conOpt);
		MqttMessage message = new MqttMessage(payload);
		message.setQos(0);
		client.publish(topicName, message);
		Log.logDebug("LocalPublishUtils", "publish() <end>");
	}

	/**
	 * 
	 * @param operation
	 * @param allPoints
	 */
	public static void publishToAllPoints(List<String> allPointSubscribers, String operation) {
		Log.logDebug("LocalPublishUtils", "publishToAllPoints() <start>");
		MqttClient localClient = createLocalClient();
		if (StringUtils.equals(operation, HubConstants.START_OF_DAY)) {
			for (String subscriberTopic : allPointSubscribers) {
				try {
					publish(subscriberTopic, new String(HubConstants.SE).getBytes(StandardCharsets.UTF_8), localClient);
					Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client publish <SUCCESS>");
					localClient.disconnect();
					Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client disconnect <SUCCESS>");
				} catch (MqttException e) {
					Log.logError("LocalPublishUtils", "publishToAllPoints()", e);
				} catch (Exception ex) {
					Log.logFatal("LocalPublishUtils", "FATAL ERROR in publishToAllPoints()", ex);
				}
			}
		} else if (StringUtils.equals(operation, HubConstants.END_OF_DAY)) {
			for (String subscriberTopic : allPointSubscribers) {
				try {
					publish(subscriberTopic, new String(HubConstants.DE).getBytes(StandardCharsets.UTF_8), localClient);
					Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client publish <SUCCESS>");
					localClient.disconnect();
					Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client disconnect <SUCCESS>");
				} catch (MqttException e) {
					Log.logError("LocalPublishUtils", "publishToAllPoints()", e);
				} catch (Exception ex) {
					Log.logFatal("LocalPublishUtils", "FATAL ERROR in publishToAllPoints()", ex);
				}
			}
		} else if (StringUtils.equals(operation, HubConstants.DATA)) {
			for (String subscriberTopic : allPointSubscribers) {
				try {
					publish(subscriberTopic, new String(HubConstants.TE).getBytes(StandardCharsets.UTF_8), localClient);
					Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client publish <SUCCESS>");
					localClient.disconnect();
					Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client disconnect <SUCCESS>");
				} catch (MqttException e) {
					Log.logError("LocalPublishUtils", "publishToAllPoints()", e);
				} catch (Exception ex) {
					Log.logFatal("LocalPublishUtils", "FATAL ERROR in publishToAllPoints()", ex);
				}
			}
		}
		Log.logDebug("LocalPublishUtils", "publishToAllPoints() <start>");
	}

	/**
	 * 
	 * @param topicName
	 * @param operation
	 */
	public static void publishMessage(String topicName, String operation) {
		Log.logDebug("LocalPublishUtils", "publishMessage() <start>");
		MqttClient localClient = createLocalClient();
		if (StringUtils.equals(operation, HubConstants.ON)) {
			try {
				publish(topicName, new String(HubConstants.ONE_E).getBytes(StandardCharsets.UTF_8), localClient);
				Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client publish <SUCCESS>");
				localClient.disconnect();
				Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client disconnect <SUCCESS>");
			} catch (MqttException e) {
				Log.logError("LocalPublishUtils", "publishMessage()", e);
			} catch (Exception ex) {
				Log.logFatal("LocalPublishUtils", "FATAL ERROR in publishMessage()", ex);
			}
		} else if (StringUtils.equals(operation, HubConstants.OFF)) {
			try {
				publish(topicName, new String(HubConstants.ZERO_E).getBytes(StandardCharsets.UTF_8), localClient);
				Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client publish <SUCCESS>");
				localClient.disconnect();
				Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client disconnect <SUCCESS>");
			} catch (MqttException e) {
				Log.logError("LocalPublishUtils", "publishMessage()", e);
			} catch (Exception ex) {
				Log.logFatal("LocalPublishUtils", "FATAL ERROR in publishMessage()", ex);
			}
		} else if (StringUtils.equals(operation, HubConstants.DATA)) {
			try {
				publish(topicName, new String(HubConstants.TE).getBytes(StandardCharsets.UTF_8), localClient);
				Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client publish <SUCCESS>");
				localClient.disconnect();
				Log.logInfo("LocalPublishUtils", "Local Publish Mqtt Client disconnect <SUCCESS>");
			} catch (MqttException e) {
				Log.logError("LocalPublishUtils", "publishMessage()", e);
			} catch (Exception ex) {
				Log.logFatal("LocalPublishUtils", "FATAL ERROR in publishMessage()", ex);
			}
		}
		Log.logDebug("LocalPublishUtils", "publishMessage() <end>");
	}
}