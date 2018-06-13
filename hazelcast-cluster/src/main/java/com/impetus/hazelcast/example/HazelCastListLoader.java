
/**
 * Name           : HazelCastListLoader.java
 * Type           : JAVA
 * Purpose        : Entry point to load sample list into hazelcast
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.impetus.hazelcast.example;

import com.impetus.hazelcast.LogUtils;

import java.util.List;

import org.slf4j.Logger;

/**
 * Entry point for loading sample hazelcast list. This class initiates Reader
 * class which instantiates the client and returns an emptry HashMap. The class
 * then loaders dummy key value pairs into the map
 * @author sameena.parveen
 */

public class HazelCastListLoader {

	private static Logger logger =
			LogUtils.getLogger(HazelCastListLoader.class);

	/**
	 * Starting point of Hazelcast List Loader
	 * @param args
	 *            .
	 */
	public static void main(final String[] args) {
		new HazelCastListLoader().loadHazelCastList();
	}

	/**
	 * Method to instantiate and load dummy list.
	 * @return Size of list .
	 */
	public int loadHazelCastList() {
		final int thousand = 1000;
		logger.info("Going to load hazelcast list");
		final List<String> testList;
		final Reader cacheInst = new Reader();
		testList = cacheInst.initializeTestList();
		for (int i = 0; i < thousand; i++) {
			testList.add("List" + i);
		}
		logger.info("Loaded hazelcast list");
		return testList.size();
	}

}
