package com.hazelcast.ui.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.util.CacheInstance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JMX Service class.
 * @author Sourav Gulati
 * @Description Service class for webservices related to JMX library
 */

@Component
public class JMXService {
  
  private static final Logger logger = Logger.getLogger(JMXService.class);
  private ObjectMapper objectMapper;
  
  @Autowired
  private CacheInstance cacheInstance;

  @PostConstruct
  private void intialize() {
    objectMapper = new ObjectMapper();

  }

  /**
   * Returns node memory info.
   * @Description This method is used get memory 
   *     information of hazelcast node by 
   *     calling jmx mbean(Memory)
   * @param host jmx host
   * @param port jmx port
   * @return json value of node memory stats
   */
  public String getNodeMemoryInfo(String host, String port) {
    
    logger.debug("Going to fetch node memory info");
    
    final Map<String, String> memoryMap = new HashMap<>();
    MBeanServerConnection mBeanServerConnection = null;

    try {
      JMXConnector jmxConnector = cacheInstance.getConnectionForHost(host);

      if (jmxConnector == null) {
        jmxConnector = connect(host, port);
        cacheInstance.setConnectionForHost(host, jmxConnector);
      }

      try {
        mBeanServerConnection = jmxConnector.getMBeanServerConnection();
      } catch (IOException ex) {
        // this means that the underlying connection was not correct, do
        // a reconnect
        logger.error("Exception occured. Cause is {} ",ex);
        jmxConnector = connect(host, port);
        cacheInstance.setConnectionForHost(host, jmxConnector);
        mBeanServerConnection = jmxConnector.getMBeanServerConnection();
      }

      CompositeData cd = (CompositeData) mBeanServerConnection
          .getAttribute(new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
      memoryMap.put("HOST_NAME", host);
      Long usedMem = (Long) cd.get("used");
      usedMem = (usedMem / 1024 / 1024);
      memoryMap.put("USED_MEMORY", String.valueOf(usedMem));

      Long committedMem = (Long) cd.get("committed");
      committedMem = (committedMem / 1024 / 1024);
      memoryMap.put("COMMITTED_MEMORY", String.valueOf(committedMem));

      Long maxHeap = (Long) cd.get("max");
      maxHeap = (maxHeap / 1024 / 1024);
      memoryMap.put("TOTAL_HEAP_MEMORY", String.valueOf(maxHeap));
    } catch (AttributeNotFoundException | InstanceNotFoundException
        | MalformedObjectNameException | MBeanException
        | ReflectionException | IOException e) {
      logger.error("Issue while retriving memory info. Cause is {} ",e);
    }
    try {
      
      logger.debug("Fetched node memory info successfully");
      
      return objectMapper.writeValueAsString(memoryMap);
    } catch (JsonProcessingException e) {
      return "Exception while retriving memory info" + e;
    }
  }
  
  /**
   * Returns map memory stats.
   * @Description This method is used get memory 
   *     information of hazelcast map by calling jmx mbean(Imap) 
   * @param host jmx host
   * @param port jmx port
   * @param mapName map name
   * @return json value of map memory stats
   */
  public String getMapMemoryStats(String host, String port,String mapName) {
    
    logger.debug("Going to fetch Map memory info");
    
    Map<String, String> mapMemoryMap = new HashMap<>();
    
    try {
      JMXConnector jmxConnector = cacheInstance.getConnectionForHost(host);

      if (jmxConnector == null) {
        jmxConnector = connect(host, port);
        cacheInstance.setConnectionForHost(host, jmxConnector);
      }

      MBeanServerConnection mBeanServerConnection = null;
      try {
        mBeanServerConnection = jmxConnector.getMBeanServerConnection();
      } catch (IOException ex) {
        // this means that the underlying connection was not correct, do
        // a reconnect
        logger.error("Issue while retriving memory info. Cause is {} ",ex);
        jmxConnector = connect(host, port);
        cacheInstance.setConnectionForHost(host, jmxConnector);
        mBeanServerConnection = jmxConnector.getMBeanServerConnection();
      }

      String objectName = createObjectName(mapName);
      mapMemoryMap.put("HOST", host);
      mapMemoryMap.put("ENTRIES",
          getMapAttributeValue(mBeanServerConnection, objectName, "localOwnedEntryCount"));
      mapMemoryMap.put("BACKUPS",
          getMapAttributeValue(mBeanServerConnection, objectName, "localBackupEntryCount"));
      mapMemoryMap.put("ENTRY_MEMORY",
          getMapAttributeValue(mBeanServerConnection, objectName, "localOwnedEntryMemoryCost"));
      mapMemoryMap.put("BACKUP_MEMORY",
          getMapAttributeValue(mBeanServerConnection, objectName, "localBackupEntryMemoryCost"));
    } catch (AttributeNotFoundException | InstanceNotFoundException 
        | MalformedObjectNameException | MBeanException
        |  ReflectionException | IOException e) {
      logger.error("Issue while retriving memory info. Cause is {}", e);
    }
    
    try {
      logger.debug("Fetched map memory info successfully");
      return objectMapper.writeValueAsString(mapMemoryMap);
    } catch (JsonProcessingException e) {
      return "Exception while retriving memory info" + e;
    }
    
  }

  /**
   * @Description This method is used get information 
   *     related to hazelcast map by calling jmx mbean(Imap) 
   * @param jmx connector instance
   * @param objectName
   * @param attribute
   * @return
   * @throws MBeanException exception exception
   * @throws AttributeNotFoundException
   * @throws InstanceNotFoundException
   * @throws ReflectionException
   * @throws IOException
   * @throws MalformedObjectNameException
   */
  private String getMapAttributeValue(
      MBeanServerConnection mBeanServerConnection, String objectName,
      String attribute) throws MBeanException, 
      AttributeNotFoundException, InstanceNotFoundException,
      ReflectionException, IOException, MalformedObjectNameException {
    Long value = (Long) mBeanServerConnection.getAttribute(new ObjectName(objectName), attribute);
    return String.valueOf(value);
  }
  
  /**
   * Creates map object.
   * @Description This method is used construct jmx object.
   * @param map name
   * @return object name
   */
  private static String createObjectName(String mapName) {
    return "com.hazelcast:instance=_hzInstance_1_dev,type=IMap,name=" + mapName;
  }

  private static JMXConnector connect(String host, 
      String port) throws IOException, MalformedURLException {
    return JMXConnectorFactory.connect(createConnectionURL(host, port));
  }

  
  /**
   * Returns connection url.
   * @Description This method is used create jmx connection url.
   * @param jmx host
   * @param jmx port
   * @return JMXServiceURL
   * @throws MalformedURLException exception
   */
  private static JMXServiceURL createConnectionURL(String host, String port) 
      throws MalformedURLException {
    return new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");

  }

}
