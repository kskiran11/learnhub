package com.hub.shop.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hub.shop.common.log.Log;
import com.hub.shop.common.service.LocalSchedulerConfig;
import com.hub.shop.local.service.LocalShopService;
import com.hub.shop.publicc.service.PublicShopService;

/**
 * Main class responsible ...
 * 
 * @author Kiran
 *
 */
public class RunBrokerApp {

	/**
	 * MAIN METHOD.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Log.logDebug("RunBrokerApp", "main() <Start>");
		Log.logInfo("RunBrokerApp", "Launching Hub ... ");
		new Thread() {
			@SuppressWarnings("resource")
			public void run() {
				new AnnotationConfigApplicationContext(LocalSchedulerConfig.class);
			}
		}.start();
		new Thread() {
			public void run() {
				new LocalShopService();
			}
		}.start();
		new Thread() {
			public void run() {
				new PublicShopService();
			}
		}.start();
		Log.logDebug("RunBrokerApp", "main() <end>");
	}
}
