package com.hub.shop.local.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.hub.shop.common.model.PointData;
import com.hub.shop.common.utils.PropertyUtils;
import com.hub.shop.local.utils.LocalJsonUtils;

/**
 * 
 * @author Kiran
 *
 */
public class LocalMqttService implements MqttCallback {

	List<PointData> pointData = new ArrayList<PointData>();
	private Integer pointCount = 0;
	private Integer w7PointCount = 0;

	public LocalMqttService() {
	}

	public LocalMqttService(List<String> points) {
		this.pointCount = points.size();
		MqttAsyncClient localClient = createLocalClient();
		createLocalSubscribers(localClient, points);
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

	/**
	 * 
	 * @param localClient
	 * @param points
	 */
	private void createLocalSubscribers(MqttAsyncClient localClient, List<String> points) {
		Log.logDebug(this, "createLocalSubscribers() <start>");
		boolean cleanSession = true;
		MqttConnectOptions conOptions = null;
		conOptions = new MqttConnectOptions();
		conOptions.setCleanSession(cleanSession);
		IMqttToken conToken = null;
		try {
			conToken = localClient.connect(conOptions, null, null);
			conToken.waitForCompletion();
			Log.logInfo(this, "Local MQTT client connection <SUCCESS>");
		} catch (MqttSecurityException e) {
			Log.logError(this, "Mqtt Security Exception in createLocalSubscribers()", e);
		} catch (MqttException ex) {
			Log.logError(this, "Mqtt Exception in createLocalSubscribers()", ex);
		} catch (Exception exc) {
			Log.logFatal(this, "FATAL EXCEPTION in createLocalSubscribers()", exc);
		}
		for (String point : points) {
			try {
				IMqttToken subToken = null;
				subToken = localClient.subscribe(point.toString(), 0, null, null);
				subToken.waitForCompletion();
			} catch (MqttException e) {
				Log.logError(this, "Mqtt Exception in createLocalSubscribers()", e);
			} catch (Exception ex) {
				Log.logError(this, "Mqtt Exception in createLocalSubscribers()", ex);
			}
		}
		Log.logInfo(this, "Local MQTT subscriptions <SUCCESS> for " + points.size() + " publishers");
		Log.logDebug(this, "createLocalSubscribers() <end>");
	}

	/**
	 * 
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Log.logDebug(this, "messageArrived() <start>");
		String payLoad = new String(message.getPayload(), StandardCharsets.UTF_8);
		System.out.println(message.getPayload());
		if (StringUtils.equals(payLoad, HubConstants.END_DATA_TX)) {
			Log.logInfo(this, "Received W7, proceeding to persist date into local db");
			pointData.add(new PointData(topic, payLoad, new Long(Calendar.getInstance().getTimeInMillis()).toString()));
			w7PointCount++;
			if (w7PointCount == pointCount) {
				List<PointData> clonedPointData = new ArrayList<PointData>(pointData.size());
				cloneData(pointData, clonedPointData);
				pointData.clear();
				new Thread() {
					public void run() {
						String payLoadString = LocalJsonUtils.convertToJson(clonedPointData);
						new LocalCommonService().insertData(payLoadString);
					}
				}.start();
			}
		} else {
			pointData.add(new PointData(topic, payLoad, ""));
		}
		Log.logDebug(this, "messageArrived() <end>");
	}

	/**
	 * 
	 */
	@Override
	public void connectionLost(Throwable cause) {
		Log.logError(this, "connectionLost()", cause);
	}

	/**
	 * 
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		Log.logDebug(this, "deliveryComplete()");
	}

	/**
	 * 
	 * @param originalList
	 * @param clonedList
	 */
	private void cloneData(List<PointData> originalList, List<PointData> clonedList) {
		Log.logDebug(this, "cloneData() <start>");
		for (PointData data : originalList) {
			clonedList.add(new PointData(data));
		}
		Log.logDebug(this, "cloneData() <end>");
	}
}
