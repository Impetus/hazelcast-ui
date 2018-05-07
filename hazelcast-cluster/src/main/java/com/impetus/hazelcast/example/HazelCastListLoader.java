
/**
 * Name           : HazelCastListLoader.java
 * Type           : JAVA
 * Purpose        : Entry point to load sample list into hazelcast
 * Description    : 
 * Mod Log
 * Date		    By		         	Jira			Description
 * ----------- 	----------------- 	---------- 		---------------	
**/

package com.impetus.hazelcast.example;

import java.util.List;

import org.slf4j.Logger;

import com.impetus.hazelcast.LogUtils;

/**
 * Entry point for loading sample hazelcast list
 * This class initiates Reader class which instantiates the client and returns an emptry HashMap.
 * The class then loaders dummy key value pairs into the map
 * @author sameena.parveen
 *
 */

public class HazelCastListLoader {

	
	private static Logger logger = LogUtils.getLogger(HazelCastListLoader.class);
	
	public static void main(String[] args) {
		HazelCastListLoader.loadHazelCastList();
	}
	
	/**
	 * Method to instantiate and load dummy list
	 */
	public static void loadHazelCastList() {
		logger.info("Going to load hazelcast list");
		List<String> testList;
		Reader cacheInst = new Reader();
		testList = cacheInst.initializeTestList();
		for(int i=0;i<1000;i++) {
			testList.add("List"+i);
		}
		logger.info("Loaded hazelcast list");	
	}
	

}
