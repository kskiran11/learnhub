package com.hub.shop.local.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.ShopData;
import com.hub.shop.common.utils.CommonShopUtils;
import com.hub.shop.common.utils.HttpUtils;
import com.hub.shop.common.utils.ShopPointApplianceUtils;
import com.hub.shop.local.utils.LocalPublishUtils;
import com.hub.shop.publicc.service.PublicCommonService;

/**
 * Medium
 * 
 * @author Kiran
 *
 */
public class LocalMqttValidateAndPublish {

	PublicCommonService commonService = new PublicCommonService();

	public LocalMqttValidateAndPublish() {

	}

	public LocalMqttValidateAndPublish(MqttMessage message) {
		Log.logDebug(this, "LocalMqttValidateAndPublish() <start>");
		Log.logInfo(this, "Received Payload :: " + message);
		if (isValidPayload(message)) {
			String[] messages = StringUtils.split(new String(message.getPayload(), StandardCharsets.UTF_8),
					HubConstants.UNDERSCORE);
			switch (messages[1]) {
			case HubConstants.ALL:
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				switch (messages[2]) {
				case HubConstants.START_OF_DAY:
					LocalPublishUtils.publishToAllPoints(createPointSubscriberList(), HubConstants.START_OF_DAY);
					params.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM, HubConstants.HTTP_START_OF_DAY));
					params.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
					HttpUtils.httpPost(params);
					break;
				case HubConstants.END_OF_DAY:
					LocalPublishUtils.publishToAllPoints(createPointSubscriberList(), HubConstants.END_OF_DAY);
					params.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM, HubConstants.HTTP_END_OF_DAY));
					params.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
					HttpUtils.httpPost(params);
					break;
				case HubConstants.DATA:
					if (StringUtils.equals(messages[0], HubConstants.OPN)) {
						LocalPublishUtils.publishToAllPoints(createPointSubscriberList(), HubConstants.DATA);
						params.add(
								new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM, HubConstants.HTTP_DATA_LOAD));
						params.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
						HttpUtils.httpPost(params);
					} else {
						List<ShopData> publicDataList = new LocalCommonService().fetchData(messages[0]);
						String date = StringUtils.EMPTY;
						if (publicDataList.size() > 0) {
							date = publicDataList.get(0).getDate();
							new PublicCommonService().persistData(publicDataList);
							new LocalCommonService().deleteTableContents(date);
						}
						params.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								HubConstants.HTTP_DATA_PERSIST));
						params.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
						HttpUtils.httpPost(params);
					}

					break;
				default:
					break;
				}
				break;
			default:
				String subscriberTopic = getSubscriberTopicFromAppliance(messages[1]);
				List<BasicNameValuePair> httpParams = new ArrayList<BasicNameValuePair>();
				switch (messages[2]) {
				case HubConstants.ON:
					if (StringUtils.containsIgnoreCase(subscriberTopic, HubConstants.ERROR)) {
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								new StringBuffer(messages[1]).append(HubConstants.HTTP__ON).toString()));
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_ERROR_1));
						HttpUtils.httpPost(httpParams);
					} else {
						LocalPublishUtils.publishMessage(subscriberTopic, HubConstants.ON);
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								new StringBuffer(messages[1]).append(HubConstants.HTTP__ON).toString()));
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
						HttpUtils.httpPost(httpParams);
					}
					break;
				case HubConstants.OFF:
					if (StringUtils.containsIgnoreCase(subscriberTopic, HubConstants.ERROR)) {
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								new StringBuffer(messages[1]).append(HubConstants.HTTP__OFF).toString()));
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_ERROR_1));
						HttpUtils.httpPost(httpParams);
					} else {
						LocalPublishUtils.publishMessage(subscriberTopic, HubConstants.OFF);
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								new StringBuffer(messages[1]).append(HubConstants.HTTP__OFF).toString()));
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
						HttpUtils.httpPost(httpParams);
					}
					break;
				case HubConstants.DATA:
					if (StringUtils.containsIgnoreCase(subscriberTopic, HubConstants.ERROR)) {
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								new StringBuffer(messages[1]).append(HubConstants.HTTP__DATA).toString()));
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_ERROR_1));
						HttpUtils.httpPost(httpParams);
					} else {
						LocalPublishUtils.publishMessage(subscriberTopic, HubConstants.DATA);
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM,
								new StringBuffer(messages[1]).append(HubConstants.HTTP__DATA).toString()));
						httpParams.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_SUCCESS));
						HttpUtils.httpPost(httpParams);
					}
					break;
				default:
					break;
				}
			}
		} else {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair(HubConstants.HTTP_MESSAGE_PARAM, HubConstants.HTTP_NA));
			params.add(new BasicNameValuePair(HubConstants.HTTP_RESP_PARAM, HubConstants.HTTP_ERROR_2));
			HttpUtils.httpPost(params);
		}
		Log.logDebug(this, "LocalMqttValidateAndPublish() <end>");
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	private boolean isValidPayload(MqttMessage message) {
		Log.logDebug(this, "IN isValidPayload()");
		if (message.getPayload() == null) {
			return false;
		}
		String payLoad = new String(message.getPayload(), StandardCharsets.UTF_8);
		if (StringUtils.isBlank(payLoad) || StringUtils.isEmpty(payLoad)) {
			return false;
		} else if (StringUtils.startsWith(payLoad, HubConstants.UNDERSCORE)) {
			return false;
		} else if (StringUtils.endsWith(payLoad, HubConstants.UNDERSCORE)) {
			return false;
		} else if (!(StringUtils.split(payLoad, HubConstants.UNDERSCORE).length == 3)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> createPointSubscriberList() {
		Log.logDebug(this, "createPointSubscriberList() <start>");
		List<String> pointSubscribers = new ArrayList<String>();
		for (String pointId : getAllPoints()) {
			pointSubscribers.add(new StringBuffer(pointId).append(HubConstants.IN).toString());
		}
		Log.logDebug(this, "createPointSubscriberList() <end>");
		return pointSubscribers;
	}

	/**
	 * 
	 * @param appliance
	 * @return
	 */
	private String getSubscriberTopicFromAppliance(String appliance) {
		Log.logDebug(this, "IN getSubscriberTopicFromAppliance()");
		return new StringBuffer(ShopPointApplianceUtils.getPointIdUsingAppliance(appliance)).append(HubConstants.IN)
				.toString();
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getAllPoints() {
		Log.logDebug(this, "IN getAllPoints()");
		String shopName = CommonShopUtils.getShopName();
		String shopId = commonService.getshopId(shopName);
		return commonService.getShopPoints(shopId);
	}
}
