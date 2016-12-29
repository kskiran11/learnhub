package com.hub.shop.common.model;

/**
 * Class to hold all incoming payload.
 * 
 * topic will hold the topic and payload will hold the mqtt payload received
 * 
 * @author Kiran
 *
 */
public class PointData {

	private String topic;
	private String payLoad;
	private String timeStamp = "";

	public PointData() {

	}

	public PointData(String topic, String payLoad, String timeStamp) {
		this.topic = topic;
		this.payLoad = payLoad;
		this.timeStamp = timeStamp;
	}

	public PointData(PointData pointData) {
		this.topic = pointData.getTopic();
		this.payLoad = pointData.getPayLoad();
		this.timeStamp = pointData.getTimeStamp();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}