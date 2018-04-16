package com.impetus.hazelcast;

import java.util.Map;

public class HazelcastMapLoader {
	static private Reader cacheInst = null;
	static private Map<String, String> testMap = null;
	public static void main(String[] args) {
		System.out.println("Loading started");
		
		HazelcastMapLoader loader = new HazelcastMapLoader();
		loader.cacheInst = new Reader();
		testMap = cacheInst.initializeTestMap();
		
		for(int i=0;i<1000;i++) {
			
			testMap.put("Key"+i,"Value"+i);

		}
		System.out.println("Loading End");

	
	
}
}
