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
 * Entry point for loading sample hazelcast maps.
 * This class initiates Reader class which instantiates the client and returns an emptry HashMap.
 * The class then loaders dummy key value pairs into the map
 * @author pushkin.gupta
 */
public class HazelcastMapLoader {
  
  private static Logger logger = LogUtils.getLogger(HazelcastMapLoader.class);
  private static Map<String, String> testMap;
  
  public static void main(String[] args) {
    HazelcastMapLoader.loadHazelCastMap();
  }
  
  /**
   * Method to instantiate and load dummy map.
   */
  public static int loadHazelCastMap() {
    logger.info("Going to load hazelcast maps");
    
    Reader cacheInst = null;
    
    cacheInst = new Reader();
    testMap = cacheInst.initializeTestMap();
    for (int i = 0;i < 1000;i++) {
      testMap.put("Key" + i,"Value" + i);
    }
    logger.info("Loaded hazelcast maps");
    return testMap.size();
    
  }
  
}
