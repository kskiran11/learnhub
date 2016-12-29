package com.hub.shop.publicc.service;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Class to insert data into public db
 * 
 * @author Kiran
 *
 */
public class PublicDataPersist {

	/**
	 * Method to persist all the saved data in <pointData> into local sqlite db.
	 * Later call the public utils and persist the same in public data
	 */
	@Scheduled(fixedDelay = 360000)
	public void persistDataIntoHub() {

		//TO DO
		
	}
}