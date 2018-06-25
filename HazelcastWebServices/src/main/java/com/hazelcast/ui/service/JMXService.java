package com.hazelcast.ui.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.util.CacheInstance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

	private static final Logger LOGGER = Logger.getLogger(JMXService.class);
	private ObjectMapper objectMapper;

	@Autowired
	private CacheInstance cacheInstance;
	private JMXConnector jmxConnector;
	private MBeanServerConnection mBeanServerConnection;
	private CompositeData cd;

	/**
	 * Constructor
	 * Moved insantiation of objectMapper from @PostContruct
	 * annotation to constructor
	 * as the postConstuct was not getting called when invoking class using Mockito
	 */
	public JMXService() {
		objectMapper = new ObjectMapper();
	}


	/**
	 * Method initializes ObjectMapper .
	 */
	@PostConstruct
	private void intialize() {

	}

	/**
	 * Returns node memory info.
	 * @Description This method is used get memory information of hazelcast node
	 *              by calling jmx mbean(Memory)
	 * @param host
	 *            jmx host
	 * @param port
	 *            jmx port
	 * @return json value of node memory stats
	 */
	public String getNodeMemoryInfo(final String host, final String port) {
		final int number = 1024;
		LOGGER.debug("Going to fetch node memory info");

		// Changed from Hashmap to use LinkedHashMap to maintain order of insertion
		// This is needed as its checked in Junit
		final Map<String, String> memoryMap = new LinkedHashMap<String, String>();

		try {
			jmxConnector =
					cacheInstance.getConnectionForHost(host);

			if (jmxConnector == null) {
				jmxConnector = connect(host, port);
				cacheInstance.setConnectionForHost(host, jmxConnector);
			}

			try {
				if (mBeanServerConnection == null) {
					mBeanServerConnection = jmxConnector.getMBeanServerConnection();
				}
			} catch (final IOException ex) {
				// this means that the underlying connection was not correct, do
				// a reconnect
				LOGGER.error("Exception occured");
				jmxConnector = connect(host, port);
				cacheInstance.setConnectionForHost(host, jmxConnector);
				mBeanServerConnection = jmxConnector.getMBeanServerConnection();
			}

			cd = (CompositeData) mBeanServerConnection
					.getAttribute(new ObjectName("java.lang:type=Memory"),
							"HeapMemoryUsage");
			memoryMap.put("HOST_NAME", host);
			Long usedMem = (Long) cd.get("used");
			usedMem = (usedMem / number / number);
			memoryMap.put("USED_MEMORY", String.valueOf(usedMem));

			Long committedMem = (Long) cd.get("committed");
			committedMem = (committedMem / number / number);
			memoryMap.put("COMMITTED_MEMORY", String.valueOf(committedMem));

			Long maxHeap = (Long) cd.get("max");
			maxHeap = (maxHeap / number / number);
			memoryMap.put("TOTAL_HEAP_MEMORY", String.valueOf(maxHeap));
		} catch (AttributeNotFoundException | InstanceNotFoundException
				| MalformedObjectNameException | MBeanException
				| ReflectionException | IOException e) {
			LOGGER.error("Issue while retriving memory info.");
			return "Exception occurred when retriving memory info";
		}
		try {

			LOGGER.debug("Fetched node memory info successfully");

			return objectMapper.writeValueAsString(memoryMap);
		} catch (final JsonProcessingException e) {
			return "Exception while retriving memory info" + e;
		}
	}

	/**
	 * Returns map memory stats.
	 * @Description This method is used get memory information of hazelcast map
	 *              by calling jmx mbean(Imap)
	 * @param host
	 *            jmx host
	 * @param port
	 *            jmx port
	 * @param mapName
	 *            map name
	 * @return json value of map memory stats
	 */
	public String getMapMemoryStats(final String host,
			final String port, final String mapName) {

		LOGGER.debug("Going to fetch Map memory info");

		final Map<String, String> mapMemoryMap = new LinkedHashMap<String, String>();

		try {
			jmxConnector =
					cacheInstance.getConnectionForHost(host);

			if (jmxConnector == null) {
				jmxConnector = connect(host, port);
				cacheInstance.setConnectionForHost(host, jmxConnector);
			}

			try {
				mBeanServerConnection = jmxConnector.getMBeanServerConnection();
			} catch (final IOException ex) {
				// this means that the underlying connection was not correct, do
				// a reconnect
				LOGGER.error("Issue while retriving memory info.");
				jmxConnector = connect(host, port);
				cacheInstance.setConnectionForHost(host, jmxConnector);
				mBeanServerConnection = jmxConnector.getMBeanServerConnection();
			}

			final String objectName = createObjectName(mapName);
			mapMemoryMap.put("HOST", host);
			mapMemoryMap.put("ENTRIES",
					getMapAttributeValue(mBeanServerConnection, objectName,
							"localOwnedEntryCount"));
			mapMemoryMap.put("BACKUPS",
					getMapAttributeValue(mBeanServerConnection, objectName,
							"localBackupEntryCount"));
			mapMemoryMap.put("ENTRY_MEMORY",
					getMapAttributeValue(mBeanServerConnection, objectName,
							"localOwnedEntryMemoryCost"));
			mapMemoryMap.put("BACKUP_MEMORY",
					getMapAttributeValue(mBeanServerConnection, objectName,
							"localBackupEntryMemoryCost"));
		} catch (AttributeNotFoundException | InstanceNotFoundException
				| MalformedObjectNameException | MBeanException
				| ReflectionException | IOException e) {
			LOGGER.error("Issue while retriving memory info.");
			return "Exception while retriving memory info";
		}

		try {
			LOGGER.debug("Fetched map memory info successfully");
			return objectMapper.writeValueAsString(mapMemoryMap);
		} catch (final JsonProcessingException e) {
			return "Exception while retriving memory info";
		}

	}

	/**
	 * @Description This method is used get information related to hazelcast map
	 *              by calling jmx mbean(Imap)
	 * @return Map's attribute
	 *            connector instance
	 * @param objectName .
	 * @param attribute .
	 * @param mBeanServerConnection .
	 * @return
	 * @throws MBeanException
	 *             exception exception
	 * @throws AttributeNotFoundException .
	 * @throws InstanceNotFoundException .
	 * @throws ReflectionException .
	 * @throws IOException .
	 * @throws MalformedObjectNameException .
	 */
	private String getMapAttributeValue(
			final MBeanServerConnection mBeanServerConnection, final String objectName,
			final String attribute) throws MBeanException,
			AttributeNotFoundException, InstanceNotFoundException,
			ReflectionException, IOException, MalformedObjectNameException {
		final Long value = (Long) mBeanServerConnection
				.getAttribute(new ObjectName(objectName), attribute);
		return String.valueOf(value);
	}

	/**
	 * Creates map object.
	 * @Description This method is used construct jmx object.
	 * @param mapName .
	 *            name
	 * @return object name
	 */
	private static String createObjectName(final String mapName) {
		return "com.hazelcast:instance=_hzInstance_1_dev,type=IMap,name="
				+ mapName;
	}

	/**
	 * @Description This method is used to create JMX connector.
	 * @param host .
	 * @param port .
	 * @return JMXConnector
	 * @throws IOException .
	 * @throws MalformedURLException .
	 */
	private static JMXConnector connect(final String host,
			final String port)
					throws IOException {
		return JMXConnectorFactory.connect(createConnectionURL(host, port));
	}

	/**
	 * Returns connection url.
	 * @Description This method is used create jmx connection url.
	 * @param host .
	 * @param port .
	 * @return JMXServiceURL
	 * @throws MalformedURLException
	 *             exception
	 */
	private static JMXServiceURL createConnectionURL(final String host,
			final String port)
			throws MalformedURLException {
		return new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":"
				+ port + "/jmxrmi");

	}

}
