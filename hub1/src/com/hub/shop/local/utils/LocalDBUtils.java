package com.hub.shop.local.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hub.shop.common.log.Log;

/**
 * 
 * @author Kiran
 *
 */
public class LocalDBUtils {

	private static Connection localDBConnection = null;

	/**
	 * 
	 */
	private LocalDBUtils() {

	}

	/**
	 * get sqlite LocalDB connection
	 * 
	 * @return
	 */
	public static Connection getLocalDBConnection() {
		Log.logDebug("LocalDBUtils", "IN getLocalDBConnection()");
		if (localDBConnection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				Log.logError("LocalDBUtils", "Class NotFound Exception in getLocalDBConnection()", e);
			} catch (Exception ex) {
				Log.logFatal("LocalDBUtils", "FATAL EXCEPTION in getLocalDBConnection()", ex);
			}
			try {
				localDBConnection = DriverManager.getConnection("jdbc:sqlite:localPointData.db");
				localDBConnection.setAutoCommit(false);
			} catch (SQLException e) {
				Log.logError("LocalDBUtils", "SQL Exception in getLocalDBConnection()", e);
			} catch (Exception ex) {
				Log.logFatal("LocalDBUtils", "FATAL EXCEPTION in getLocalDBConnection()", ex);
			}
			Log.logInfo("LocalDBUtils", "Local DB connection <SUCCESS>");
			return localDBConnection;
		} else {
			return localDBConnection;
		}
	}
}
