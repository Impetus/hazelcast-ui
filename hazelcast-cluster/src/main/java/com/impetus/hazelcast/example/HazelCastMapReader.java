/**
 * Name           : HazelCastMapReader.java
 * Type           : JAVA
 * Purpose        : Entry point to read sample list from hazelcast
 * Description    : 
 * Mod Log
 * Date		    By		         	Jira			Description
 * ----------- 	----------------- 	---------- 		---------------	
**/
package com.impetus.hazelcast.example;

import java.util.Map;

import org.slf4j.Logger;

import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.LogUtils;

/**
 * Entry point for reading sample hazelcast map
 * This class initiates Reader class which instantiates the client and returns a map.
 * The class then reads dummy key value pairs from the map
 * @author sameena.parveen
 *
 */
public class HazelCastMapReader {
	private static Logger logger = LogUtils.getLogger(HazelCastMapReader.class);
	Reader cacheInst;
	HazelcastInstance client = null;
	private static Map<String, String> testMap;
	
	
	public static void main(String[] args) {
		Map<String, String> map;
		map=new HazelCastMapReader().readMap();
		logger.info("The map value is"+map.get("Key1"));
	}
	
	/**
	 * Method to instantiate and read dummy map
	 */
	public Map<String, String> readMap(){
		try {
			cacheInst = new Reader();
			testMap=cacheInst.initializeTestMap();
			
			}
			
		catch (Exception e) {
			logger.error("Error"+e);
		}
		return testMap;
		
	}
}
