package com.hub.shop.local.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;
import com.hub.shop.common.utils.DateUtils;
import com.hub.shop.local.utils.LocalDBUtils;

/**
 * DAO Class to insert data into sqlite db
 * 
 * @author Kiran
 *
 */
public class LocalDao {

	/**
	 * Method to insert data into local db
	 * 
	 * @param topicPayloadJsonString
	 */
	public void insertData(String topicPayloadJsonString) {
		Log.logDebug(this, "insertData() <start>");
		Connection localDBConnection = getConnection();
		if (tableCount(localDBConnection, HubConstants.LOCAL_TABLE_NAME) > 0) {
		} else {
			createInsertTable(localDBConnection);
		}
		insertPayloadDataString(localDBConnection, topicPayloadJsonString);
		Log.logDebug(this, "insertData() <end>");
	}

	/**
	 * Method to extract data from local db
	 * 
	 * @param date
	 */
	public List<String> fetchData(String date) {
		Log.logDebug(this, "fetchData() <start>");
		Log.logInfo(this, "Fetching local data for date :: " + date);
		List<String> localData = new ArrayList<String>();
		Connection localDBConnection = getConnection();
		String sql = "SELECT TOPICDATA FROM DATA WHERE DATADATE = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = localDBConnection.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				localData.add(rs.getString(1));
			}
			Log.logInfo(this, "Fetching local data <SUCCESS> for date :: " + date);
			localDBConnection.commit();
		} catch (SQLException e) {
			Log.logError(this, "SQL Exception in fetchData()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in fetchData()", ex);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				Log.logError(this, "SQL Exception in fetchData()", e);
			} catch (Exception ex) {
				Log.logFatal(this, "FATAL EXCEPTION in fetchData()", ex);
			}
		}
		Log.logDebug(this, "fetchData() <end>");
		return localData;
	}

	/**
	 * Method to delete data from local db
	 * 
	 * @param date
	 */
	public void deleteTableContents(String date) {
		Log.logDebug(this, "deleteTableContents() <start>");
		PreparedStatement pstmt = null;
		try {
			Connection localDBConnection = getConnection();
			String sql = "DELETE FROM DATA WHERE DATADATE LIKE ?";
			pstmt = localDBConnection.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.execute();
			localDBConnection.commit();
		} catch (SQLException e) {
			Log.logError(this, "SQL Exception in deleteTableContents()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in deleteTableContents()", ex);
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				Log.logError(this, "SQL Exception in fetchData()", e);
			} catch (Exception ex) {
				Log.logFatal(this, "FATAL EXCEPTION in fetchData()", ex);
			}
		}
		Log.logInfo(this, "DELETE FROM TABLE <SUCCESS> for date");
		Log.logDebug(this, "deleteTableContents() <end>");
	}

	/**
	 * private method.
	 * 
	 * @param localDBConnection
	 * @param tableName
	 * @return
	 */
	private Integer tableCount(Connection localDBConnection, String tableName) {
		Log.logDebug(this, "tableCount() <start>");
		Integer count = 0;
		try {
			Statement statement = localDBConnection.createStatement();
			String sql = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				count = rs.getInt(1);
				break;
			}
			statement.close();
			localDBConnection.commit();
		} catch (SQLException e) {
			Log.logError(this, "SQL Exception in tableCount()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in tableCount()", ex);
		}
		Log.logDebug(this, "tableCount() <end>");
		return count;
	}

	/**
	 * private method.
	 * 
	 * @param localDBConnection
	 */
	private void createInsertTable(Connection localDBConnection) {
		Log.logDebug(this, "createInsertTable() <start>");
		try {
			Statement statement = localDBConnection.createStatement();
			String sql = "CREATE TABLE DATA (DATADATE TEXT NOT NULL, TOPICDATA TEXT NOT NULL)";
			statement.execute(sql);
			statement.close();
			localDBConnection.commit();
		} catch (SQLException e) {
			Log.logError(this, "SQL Exception in createInsertTable()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in createInsertTable()", ex);
		}
		Log.logDebug(this, "createInsertTable() <end>");
	}

	/**
	 * private method.
	 * 
	 * @param localDBConnection
	 * @param topicPayloadList
	 */
	private void insertPayloadDataString(Connection localDBConnection, String topicPayloadList) {
		Log.logDebug(this, "insertPayloadDataString() <start>");
		try {
			Statement statement = localDBConnection.createStatement();
			String sql = "INSERT INTO DATA (DATADATE, TOPICDATA) " + "VALUES ('" + DateUtils.getTodayDate() + "', '"
					+ topicPayloadList + "')";
			statement.executeUpdate(sql);
			statement.close();
			localDBConnection.commit();
			Log.logInfo(this, "Insert local data <SUCCESS>");
		} catch (SQLException e) {
			Log.logError(this, "SQL Exception in insertPayloadDataString()", e);
		} catch (Exception ex) {
			Log.logFatal(this, "FATAL EXCEPTION in insertPayloadDataString()", ex);
		}
		Log.logDebug(this, "insertPayloadDataString() <end>");
	}

	/**
	 * Method to get db connection.
	 * 
	 * @return
	 */
	private Connection getConnection() {
		Log.logDebug(this, "IN getConnection()");
		return LocalDBUtils.getLocalDBConnection();
	}
}
