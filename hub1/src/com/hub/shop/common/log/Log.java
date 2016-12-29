package com.hub.shop.common.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 *
 * Log Class.
 * 
 * @author Kiran
 *
 */
public class Log {

	/**
	 * Private Constructor. Singleton class.
	 */
	private Log() {
	}

	/** Log instance. */
	private static final Log logger = getInstance();

	/**
	 * method to create Log instance
	 * 
	 * @return logger
	 */
	private static Log getInstance() {
		if (logger == null) {
			return new Log();
		} else {
			return logger;
		}
	}

	/**
	 * core method that logs the message into log file. Method will log message
	 * with simple class name.
	 * 
	 * @param callingObject
	 * @param log
	 * @param thrown
	 * @param logLevel
	 */
	private static void log(Object callingObject, String logMessage, Throwable thrown, Level logLevel) {
		if (callingObject == null) {
			callingObject = new Object();
		}
		Logger ologger = null;
		if (callingObject instanceof String) {
			ologger = LogManager.getLogger((String) callingObject);
		} else {
			ologger = LogManager.getLogger(callingObject.getClass().getSimpleName());
		}
		if (ologger.isEnabled(logLevel)) {
			if (thrown == null) {
				ologger.log(logLevel, logMessage);
			} else {
				ologger.log(logLevel, logMessage, thrown);
			}
		}
	}

	/**
	 * Info Log method.
	 * 
	 * @param callingObject
	 * @param infoMessage
	 */
	public static void logInfo(Object callingObject, String infoMessage) {
		log(callingObject, infoMessage, null, Level.INFO);
	}

	/**
	 * Debug Log method.
	 * 
	 * @param callingObject
	 * @param debugMessage
	 */
	public static void logDebug(Object callingObject, String debugMessage) {
		log(callingObject, debugMessage, null, Level.DEBUG);
	}

	/**
	 * Error Log method.
	 * 
	 * @param callingObject
	 * @param errorMessage
	 * @param thrown
	 */
	public static void logError(Object callingObject, String errorMessage, Throwable thrown) {
		log(callingObject, errorMessage, thrown, Level.ERROR);
	}

	/**
	 * Fatal Log method.
	 * 
	 * @param callingObject
	 * @param fatalMessage
	 * @param thrown
	 * @param logMessage
	 */
	public static void logFatal(Object callingObject, String fatalMessage, Throwable thrown) {
		log(callingObject, fatalMessage, thrown, Level.FATAL);
	}

	/**
	 * method to change log level.
	 * 
	 * @param newLevel
	 */
	public static void setLogLevel(Level newLevel) {
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		LoggerConfig config = context.getConfiguration().getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		config.setLevel(newLevel);
		context.updateLoggers();
	}

	/**
	 * method to get current log level.
	 * 
	 * @return
	 */
	public static Level getLogLevel() {
		return LogManager.getRootLogger().getLevel();
	}
}