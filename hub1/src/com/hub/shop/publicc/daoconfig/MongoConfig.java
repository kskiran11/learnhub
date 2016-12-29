package com.hub.shop.publicc.daoconfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.utils.PropertyUtils;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Class to establish DB connection
 *
 * @author Kiran
 *
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	/**
	 * 
	 */
	@Override
	protected String getDatabaseName() {
		Log.logDebug(this, "IN getDatabaseName()");
		return PropertyUtils.getPropertyValue(HubConstants.SHOP_DATA_DB_NAME);
	}

	/**
	 * 
	 */
	@Override
	@Bean
	public Mongo mongo() throws Exception {
		List<ServerAddress> serverList = new ArrayList<ServerAddress>();
		serverList.add(new ServerAddress(PropertyUtils.getPropertyValue(HubConstants.MONGO_DB_URL),
				Integer.parseInt(PropertyUtils.getPropertyValue(HubConstants.MONGO_DB_PORT))));
		List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
		credentialList.add(MongoCredential.createCredential(PropertyUtils.getPropertyValue(HubConstants.MONGO_USERNAME),
				getDatabaseName(), PropertyUtils.getPropertyValue(HubConstants.MONGO_PASSWORD).toCharArray()));
		return new MongoClient(serverList, credentialList);
	}

	/**
	 * 
	 */
	@Override
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
		return mongoTemplate;
	}
}
