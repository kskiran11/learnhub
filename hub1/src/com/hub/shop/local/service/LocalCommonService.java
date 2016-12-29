package com.hub.shop.local.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.PointData;
import com.hub.shop.common.model.ShopApplianceData;
import com.hub.shop.common.model.ShopData;
import com.hub.shop.common.utils.CommonShopUtils;
import com.hub.shop.common.utils.DateUtils;
import com.hub.shop.local.dao.LocalDao;
import com.hub.shop.local.utils.LocalJsonUtils;
import com.hub.shop.publicc.service.PublicCommonService;

/**
 * Service Class to call DAO
 * 
 * @author Kiran
 *
 */
public class LocalCommonService {

	LocalDao dao = new LocalDao();

	/**
	 * Method to insert data in sqlite
	 * 
	 * @param topicPayloadJsonString
	 */
	public void insertData(String topicPayloadJsonString) {
		Log.logDebug(this, "IN insertData()");
		dao.insertData(topicPayloadJsonString);
	}

	/**
	 * Method to fetch data from sqlite
	 * 
	 * @param date
	 * @return
	 */
	public List<ShopData> fetchData(String date) {
		Log.logDebug(this, "IN fetchData()");
		List<String> dbLocalDataList = dao.fetchData(DateUtils.getDateFromString(date));
		return extractDataApplianceWise(dbLocalDataList);
	}

	/**
	 * Method to fetch data from sqlite
	 * 
	 * @param date
	 * @return
	 */
	public void deleteTableContents(String date) {
		Log.logDebug(this, "IN deleteTableContents()");
		dao.deleteTableContents(date);
	}

	/**
	 * 
	 * @param dbLocalDataList
	 * @return
	 */
	private List<ShopData> extractDataApplianceWise(List<String> dbLocalDataList) {
		Log.logDebug(this, "extractDataApplianceWise() <start>");
		List<ShopData> publicShopDataList = new ArrayList<ShopData>();
		Map<String, List<String>> localDataMap = new HashMap<String, List<String>>();
		List<PointData> allPointsData = new ArrayList<PointData>();
		Log.logInfo(this, "Fetched Data fro local db");
		for (String data : dbLocalDataList) {
			List<PointData> pointData = LocalJsonUtils.convertFromJson(data);
			allPointsData.addAll(pointData);
		}
		addDataToMap(localDataMap, allPointsData);
		Log.logInfo(this, "Extracted data according to topic <SUCCESS>");
		for (Map.Entry<String, List<String>> entry : localDataMap.entrySet()) {
			String appliance = getAppliance(entry.getKey());
			ShopData shopData = new ShopData();
			shopData.setDate(DateUtils.getTodayDate());
			shopData.setAppliance(appliance);
			shopData.setShopName(CommonShopUtils.getShopName());
			shopData.setData(formatData(entry.getValue()));
			publicShopDataList.add(shopData);
		}
		Log.logInfo(this, "PUBLIC DATA CREATION <SUCCESS>");
		Log.logDebug(this, "extractDataApplianceWise() <end>");
		return publicShopDataList;
	}

	/**
	 * 
	 * @param topic
	 * @return
	 */
	private String getAppliance(String topic) {
		Log.logDebug(this, "IN getAppliance()");
		return new PublicCommonService().getAppliance(topic);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private List<ShopApplianceData> formatData(List<String> value) {
		Log.logDebug(this, "formatData() <start>");
		List<ShopApplianceData> applianceDataList = new ArrayList<ShopApplianceData>();
		for (int i = 0; i < value.size(); i++) {
			String[] data = StringUtils.split(value.get(i), ",");
			if (data.length == 3) {
				applianceDataList.add(new ShopApplianceData(data[0], data[1], data[2]));
			} else {
				if (applianceDataList.size() == 0) {
					applianceDataList.add(new ShopApplianceData("00", "00", ""));
				} else {
					applianceDataList.add(applianceDataList.get(applianceDataList.size() - 1));
				}

			}
		}
		Log.logDebug(this, "formatData() <end>");
		return applianceDataList;
	}

	/**
	 * 
	 * @param localDataMap
	 * @param allPointsData
	 */
	private void addDataToMap(Map<String, List<String>> localDataMap, List<PointData> allPointsData) {
		Log.logDebug(this, "IN addDataToMap()");
		for (PointData pointData : allPointsData) {
			if (localDataMap.containsKey(pointData.getTopic())) {
				List<String> existingData = localDataMap.get(pointData.getTopic());
				existingData.add(pointData.getPayLoad());
				localDataMap.put(pointData.getTopic(), existingData);
			} else {
				List<String> firstListItem = new ArrayList<String>();
				firstListItem.add(pointData.getPayLoad());
				localDataMap.put(pointData.getTopic(), firstListItem);
			}
		}
	}
}
