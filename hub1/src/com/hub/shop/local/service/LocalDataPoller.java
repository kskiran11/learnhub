package com.hub.shop.local.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import com.hub.shop.common.log.Log;
import com.hub.shop.common.model.ShopData;
import com.hub.shop.common.utils.DateUtils;
import com.hub.shop.publicc.service.PublicCommonService;

/**
 * Class to poll data from points to local db
 * 
 * @author Kiran
 *
 */
public class LocalDataPoller {

	private int counter = 0;

	/**
	 * This method calls local points and fetches data from sd cards
	 */
	// @Scheduled(fixedDelay = 360000)
	@Scheduled(fixedDelay = 20000)
	public void getDataFromPoints() {
		if (counter == 0) {
			counter++;
		} else {
			counter++;
			Log.logInfo(this, "Fetching Data from local DB");
			List<ShopData> publicDataList = new LocalCommonService().fetchData(DateUtils.getTodayDateddMMMyyyy());
			String date = StringUtils.EMPTY;
			if (publicDataList.size() > 0) {
				Log.logInfo(this, "Persisting Data into public DB");
				date = publicDataList.get(0).getDate();
				new PublicCommonService().persistData(publicDataList);
				new LocalCommonService().deleteTableContents(date);
			}
		}
	}
}