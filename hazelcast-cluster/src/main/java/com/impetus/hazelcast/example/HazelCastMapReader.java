/**
 * Name           : HazelCastMapReader.java
 * Type           : JAVA
 * Purpose        : Entry point to read sample list from hazelcast
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.impetus.hazelcast.example;

import java.util.Map;

import org.slf4j.Logger;

import com.impetus.hazelcast.LogUtils;

/**
 * Entry point for reading sample hazelcast map. This class initiates Reader
 * class which instantiates the client and returns a map. The class then reads
 * dummy key value pairs from the map
 * @author sameena.parveen
 */
public class HazelCastMapReader {
	private static Logger logger = LogUtils.getLogger(HazelCastMapReader.class);
	private Reader cacheInst;
	private static Map<String, String> testMap;

	/**
	 * Entry method.
	 * @param args
	 *            input arguments
	 */
	public static void main(final String[] args) {
		final Map<String, String> map;
		map = new HazelCastMapReader().readMap();
		logger.info("The map value is" + map.get("Key1"));
	}

	/**
	 * Method to instantiate and read dummy map.
	 * @return testMap .
	 */
	public Map<String, String> readMap() {
		try {
			cacheInst = new Reader();
			testMap = cacheInst.initializeTestMap();
		} catch (final Exception e) {
			logger.error("Error" + e);
		}
		return testMap;
	}

}
