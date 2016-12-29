package com.hub.shop.common.constants;

/**
 * App Constants
 * 
 * @author Kiran
 *
 */
public interface HubConstants {

	static final String APP_PROPERTIES = "app.properties";
	static final String SHOP_DATA_DB_NAME = "SHOP_DB_NAME";
	static final String SHOP_NAME_PATH = "SHOP_NAME_PATH";
	static final String SHOP_NAME = "SHOP_NAME";
	static final String SHOP_FILE_NAME = "/hub.properties";

	static final String LOCAL_BROKER_URL = "LOCAL_BROKER";
	static final String LOCAL_MQTT_DATASTORE_PATH = "LOCAL_MQTT_DATASTORE";
	static final String LOCAL_MQTT_LWT_DATASTORE_PATH = "LOCAL_MQTT_LWT_DATASTORE";
	static final String LOCAL_PUBLISH_MQTT_DATASTORE_PATH = "LOCAL_PUBLISH_MQTT_DATASTORE_PATH";
	static final String LOCAL_MQTT_PUBLISH_CLIENT_ID = "MqttClientPublish";

	static final String NAME = "name";
	static final String SHOP_ID = "shopId";
	static final String LOCAL_MQTT_CLIENT_ID = "MqttClient";
	static final String LOCAL_MQTT_LWT_CLIENT_ID = "MqttLWTClient";
	static final String LOCAL_MQTT_LWT_TOPIC = "lwts";
	static final String PUBLIC_BROKER_URL = "PUBLIC_BROKER";
	static final String PUBLIC_MQTT_DATASTORE_PATH = "PUBLIC_MQTT_DATASTORE";
	static final String APP_URL_HTTP = "APP_URL_HTTP";

	static final String DATE_STRING_FORMAT = "ddMMMyyyy";
	static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; rv:50.0) Gecko/20100101 Firefox/50.0";
	static final String LOCAL_TABLE_NAME = "DATA";
	static final String END_DATA_TX = "W7";

	static final String STATE_NA = "Not Available";

	static final String ALL = "ALL";
	static final String OPN = "OPN";

	static final String START_OF_DAY = "SOD";
	static final String SE = "se";
	static final String END_OF_DAY = "EOD";
	static final String DE = "de";
	static final String DATA = "DATA";
	static final String TE = "te";
	static final String ON = "ON";
	static final String ONE_E = "1e";
	static final String OFF = "OFF";
	static final String ZERO_E = "0e";

	static final String UNDERSCORE = "_";
	static final String IN = "in";
	static final String PUBLIC_MQTT_USER = "PUBLICMQTTUSER";
	static final String PUBLIC_MQTT_PASS = "HUBUser@1!";

	static final String ERROR = "ERROR";

	static final String MONGO_DB_URL = "MONGO_DB_URL";
	static final String MONGO_DB_PORT = "MONGO_DB_PORT";
	static final String MONGO_USERNAME = "MONGO_USERNAME";
	static final String MONGO_PASSWORD = "MONGO_PASSWORD";

	static final String HTTP_MESSAGE_PARAM = "message";
	static final String HTTP_RESP_PARAM = "resp";
	static final String HTTP__ON = "_ON";
	static final String HTTP__OFF = "_OFF";
	static final String HTTP__DATA = "_DATA";
	static final String HTTP_NA = "NA";
	static final String HTTP_ERROR_1 = "E101";
	static final String HTTP_ERROR_2 = "E102";
	static final String HTTP_SUCCESS = "success";
	static final String HTTP_START_OF_DAY = "Start Day";
	static final String HTTP_END_OF_DAY = "End Day";
	static final String HTTP_DATA_PERSIST = "Data Persist";
	static final String HTTP_DATA_LOAD = "Data Load";
	
	static final boolean SEND_HTTP = false;
}
