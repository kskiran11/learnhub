package com.hub.shop.common.model;

import java.util.List;

/**
 * Class to hold local data
 * 
 * date and allpoints data
 * 
 * @author Kiran
 *
 */
public class LocalTempData {
	private String date;
	private List<PointData> pointLocalTempData;

	public LocalTempData() {
	}

	public LocalTempData(String date, List<PointData> pointLocalTempData) {
		this.date = date;
		this.pointLocalTempData = pointLocalTempData;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<PointData> getPointLocalTempData() {
		return pointLocalTempData;
	}

	public void setPointLocalTempData(List<PointData> pointLocalTempData) {
		this.pointLocalTempData = pointLocalTempData;
	}

}
