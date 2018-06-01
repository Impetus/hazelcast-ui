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
}
