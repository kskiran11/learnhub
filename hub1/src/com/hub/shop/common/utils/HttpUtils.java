package com.hub.shop.common.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;

/**
 * Class to post Http Request
 * 
 * @author Kiran
 *
 */
public class HttpUtils {

	private HttpUtils() {

	}

	/**
	 * Method to send http post messages to central server
	 * 
	 * @param params
	 */
	public static void httpPost(List<BasicNameValuePair> params) {
		Log.logDebug("HttpUtils", "httpPost() <start>");
		if (HubConstants.SEND_HTTP) {
			CloseableHttpClient httpclient = HttpClients.custom().setUserAgent(HubConstants.USER_AGENT).build();
			try {
				HttpPost httpost = new HttpPost(PropertyUtils.getPropertyValue(HubConstants.APP_URL_HTTP));
				httpost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
				Log.logInfo("HttpUtils", "Making HTTP post with parameters :: " + params);
				httpclient.execute(httpost);
				Log.logInfo("HttpUtils", "HTTP post <SUCCESS>");
			} catch (IOException e) {
				Log.logError("HttpUtils", "IO Exception in httpPost()", e);
			} catch (Exception ex) {
				Log.logFatal("HttpUtils", "FATAL EXCEPTION in httpPost()", ex);
			} finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					Log.logError("HttpUtils", "IO Exception in httpPost()", e);
				} catch (Exception ex) {
					Log.logFatal("HttpUtils", "FATAL EXCEPTION in httpPost()", ex);
				}
				Log.logDebug("HttpUtils", "httpPost() <end>");
			}
		}
	}
}
