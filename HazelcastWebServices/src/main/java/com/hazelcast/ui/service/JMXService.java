package com.hazelcast.ui.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Sourav Gulati
 * @Description Service class for webservices related to JMX library
 */

@Component
public class JMXService {
	private ObjectMapper objectMapper;

	@PostConstruct
	private void intialize() {
		objectMapper = new ObjectMapper();

	}

	/**
	 * @Description This method is used get memory information of hazelcast node by calling jmx mbean(Memory)
	 * @param jmx host
	 * @param jmx port
	 * @return json value of node memory stats
	 */
	public String getNodeMemoryInfo(String host, String port) {
		Map<String, String> memoryMap = new HashMap<>();

		try {
			JMXConnector newJMXConnector;

			newJMXConnector = JMXConnectorFactory.connect(createConnectionURL(host, port));

			CompositeData cd = (CompositeData) newJMXConnector.getMBeanServerConnection().getAttribute(
					new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
			memoryMap.put("HOST_NAME",host);
			Long usedMem = (Long) cd.get("used");
			usedMem = (usedMem / 1024 / 1024);
			memoryMap.put("USED_MEMORY", String.valueOf(usedMem));

			Long committedMem = (Long) cd.get("committed");
			committedMem = (committedMem / 1024 / 1024);
			memoryMap.put("COMMITTED_MEMORY", String.valueOf(committedMem));

			Long maxHeap = (Long) cd.get("max");
			maxHeap = (maxHeap / 1024 / 1024);
			memoryMap.put("TOTAL_HEAP_MEMORY", String.valueOf(maxHeap));
		} catch (AttributeNotFoundException | InstanceNotFoundException | MalformedObjectNameException | MBeanException
				| ReflectionException | IOException e) {
			System.out.println("Issue while retriving memory info");
		}
		try {
			return objectMapper.writeValueAsString(memoryMap);
		} catch (JsonProcessingException e) {
			return "Exception while retriving memory info" + e;
		}
	}
	
	/**
	 * @Description This method is used get memory information of hazelcast map by calling jmx mbean(Imap) 
	 * @param jmx host
	 * @param jmx port
	 * @param map name
	 * @return json value of map memory stats
	 */
	public String getMapMemoryStats(String host, String port,String mapName) {
		Map<String, String> mapMemoryMap = new HashMap<>();
		
		try {
			JMXConnector newJMXConnector;
			newJMXConnector = JMXConnectorFactory.connect(createConnectionURL(host, port));
			String objectName=createObjectName(mapName);
			mapMemoryMap.put("HOST", host);
			mapMemoryMap.put("ENTRIES",getMapAttributeValue(newJMXConnector, objectName, "localOwnedEntryCount"));
			mapMemoryMap.put("BACKUPS",getMapAttributeValue(newJMXConnector, objectName, "localBackupEntryCount"));
			mapMemoryMap.put("ENTRY_MEMORY",getMapAttributeValue(newJMXConnector, objectName, "localOwnedEntryMemoryCost"));
			mapMemoryMap.put("BACKUP_MEMORY",getMapAttributeValue(newJMXConnector, objectName, "localBackupEntryMemoryCost"));
		} catch (AttributeNotFoundException | InstanceNotFoundException | MalformedObjectNameException | MBeanException
				| ReflectionException | IOException e) {
			System.out.println("Issue while retriving memory info");
		}
		
		
		try {
			return objectMapper.writeValueAsString(mapMemoryMap);
		} catch (JsonProcessingException e) {
			return "Exception while retriving memory info" + e;
		}
		
	}

	/**
	 * @Description This method is used get information related to hazelcast map by calling jmx mbean(Imap) 
	 * @param jmx connector instance
	 * @param objectName
	 * @param attribute
	 * @return
	 * @throws MBeanException
	 * @throws AttributeNotFoundException
	 * @throws InstanceNotFoundException
	 * @throws ReflectionException
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 */
	private String getMapAttributeValue(JMXConnector newJMXConnector,
			String objectName,String attribute) throws MBeanException,
			AttributeNotFoundException, InstanceNotFoundException,
			ReflectionException, IOException, MalformedObjectNameException {
		Long value= (Long) newJMXConnector.getMBeanServerConnection().getAttribute(
				new ObjectName(objectName), attribute);
		return String.valueOf(value);
	}
	
	/**
	 *  @Description This method is used construct jmx object
	 * @param map name
	 * @return object name
	 */
	private static String createObjectName(String mapName)
	{
		return "com.hazelcast:instance=_hzInstance_1_dev,type=IMap,name="+mapName;
	}

	
	/**
	 * @Description This method is used create jmx connection url  
	 * @param jmx host
	 * @param jmx port
	 * @return JMXServiceURL
	 * @throws MalformedURLException
	 */
	private static JMXServiceURL createConnectionURL(String host, String port) throws MalformedURLException {
		return new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");

	}

}
