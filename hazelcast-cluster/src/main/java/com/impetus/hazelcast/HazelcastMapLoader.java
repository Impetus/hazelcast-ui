package com.impetus.hazelcast;

import java.util.Map;

import org.slf4j.Logger;

/**
 * Entry point for loading sample hazelcast maps
 * @author pushkin.gupta
 *
 */
public class HazelcastMapLoader {
	
	private static Logger logger = LogUtils.getLogger(HazelcastMapLoader.class);
	
	public static void main(String[] args) {
		logger.info("Going to load hazelcast maps");
		
		Reader cacheInst = null;
		Map<String, String> testMap = null;
		
		cacheInst = new Reader();
		testMap = cacheInst.initializeTestMap();
		for(int i=0;i<1000;i++) {
			testMap.put("Key"+i,"Value"+i);
		}
		logger.info("Loaded hazelcast maps");
	}
}
