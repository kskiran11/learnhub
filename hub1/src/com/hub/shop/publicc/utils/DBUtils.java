package com.hub.shop.publicc.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.hub.shop.common.log.Log;
import com.hub.shop.publicc.daoconfig.MongoConfig;

/**
 *
 * @author Kiran
 *
 */
public class DBUtils {

	private static ApplicationContext context;
	private static MongoOperations mongoOperation;

	private DBUtils() {

	}

	public static MongoOperations getMongoOperationClient() {
		Log.logDebug("DBUtils", "IN getMongoOperationClient()");
		if (context == null || mongoOperation == null) {
			context = new AnnotationConfigApplicationContext(MongoConfig.class);
			mongoOperation = (MongoOperations) context.getBean("mongoTemplate");
			Log.logInfo("DBUtils", "Mongo Operations <Bean> created successfully");
			return mongoOperation;
		} else {
			return mongoOperation;
		}
	}
}