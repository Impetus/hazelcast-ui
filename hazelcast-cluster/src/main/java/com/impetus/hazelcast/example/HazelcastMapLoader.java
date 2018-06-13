/**
 * Name           : HazelcastMapLoader.java
 * Type           : JAVA
 * Purpose        : Entry point to load sample map into hazelcast
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.impetus.hazelcast.example;

import com.impetus.hazelcast.LogUtils;

import java.util.Map;

import org.slf4j.Logger;

/**
 * Entry point for loading sample hazelcast maps. This class initiates Reader
 * class which instantiates the client and returns an emptry HashMap. The class
 * then loaders dummy key value pairs into the map
 * @author pushkin.gupta
 */
 public final class HazelcastMapLoader {

	private static Logger logger = LogUtils.getLogger(HazelcastMapLoader.class);
	private static Map<String, String> testMap;

	/**
	 * Default constructor
	 */
	private HazelcastMapLoader() {
	}
	/**
	 * Main method of HazelcastMapLoader
	 * @param args .
	 */
	public static void main(final String[] args) {
		HazelcastMapLoader.loadHazelCastMap();
	}

	/**
	 * Method to instantiate and load dummy map.
	 * @return size of the map loaded .
	 */
	public static int loadHazelCastMap() {
		final int thousand = 1000;
		logger.info("Going to load hazelcast maps");
		Reader cacheInst = null;
		cacheInst = new Reader();
		testMap = cacheInst.initializeTestMap();
		for (int i = 0; i < thousand; i++) {
			testMap.put("Key" + i, "Value" + i);
		}
		logger.info("Loaded hazelcast maps");
		return testMap.size();

	}

}
