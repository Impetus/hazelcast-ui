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
		new HazelCastMapReader().readMap();
	}
	
	public void readMap(){
		try {
			cacheInst = new Reader();
			testMap=cacheInst.initializeTestMap();
			logger.info("The map value is"+testMap.get("Key1"));
			}
			
		catch (Exception e) {
			logger.error("Error"+e);
		}
		
	}
}
