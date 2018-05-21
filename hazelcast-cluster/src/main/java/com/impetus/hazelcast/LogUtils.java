package com.impetus.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class for getting the Logger instance.
 */
public class LogUtils {

  /**
   * Returns the instance of the logger.
   * 
   * @param clazz name of class
   * @return
   */
  
  private LogUtils() {
    
  }
  
  public static Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }

  public static String getCounterLog(long counter) {
    return "Events rcvd : " + counter;
  }

  public static String getIgnoredLog(String event, long counter) {
    return "Ignored log line: " + event + ", Ignored events rcvd : " + counter;
  }

  public static String getInvalidLog(String event, long counter) {
    return "Invalid event: " + event + ", Invalid events rcvd : " + counter;
  }

}
