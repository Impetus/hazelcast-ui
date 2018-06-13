/**
 * Name           : HazelCastListReader.java
 * Type           : JAVA
 * Purpose        : Entry point to read sample list from hazelcast
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.impetus.hazelcast.example;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import com.impetus.hazelcast.LogUtils;

/**
 * Entry point for reading sample hazelcast list. This class initiates Reader
 * class which instantiates the client and returns a list. The class then reads
 * dummy list.
 * @author sameena.parveen
 */

public class HazelCastListReader {
	private static Logger logger =
			LogUtils.getLogger(HazelCastListReader.class);
	private Reader cacheInst;
	private List<String> testList;

	/**
	 * Entry method.
	 * @param args
	 *            input args
	 */
	public static void main(final String[] args) {
		final List<String> list;
		list = new HazelCastListReader().readList();
		try {
			final Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				logger.info("Values of list are::" + iterator.next());
			}
		} catch (final Exception e) {
			logger.error("Error" + e);
		}
	}

	/**
	 * Method to instantiate and read dummy list.
	 * @return testList .
	 */
	public List<String> readList() {
		cacheInst = new Reader();
		testList = cacheInst.initializeTestList();
		return testList;
	}
}
