package com.impetus.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class for getting the Logger instance.
 */
public final class LogUtils {

	/**
	 * Default Constructor of LogUtils
	 */
	private LogUtils() {
	}
	/**
	 * Method to return Logger Object
	 * @return Logger .
	 * @param clazz .
	 */
	public static Logger getLogger(final Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}
}
