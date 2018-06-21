package com.hazelcast.ui.service.test;

import java.io.IOException;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.ui.service.JMXService;
import com.hazelcast.util.CacheInstance;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class JMXServiceTest {
	@InjectMocks
	private JMXService jMXService = new JMXService();
	@Mock
	private CacheInstance cacheInstance;
	@Mock
	private JMXConnector jmxConnector;
	@Mock
	private MBeanServerConnection mBeanServerConnection;
	@Mock
	private CompositeData cd;
	private static Map<Integer, String> mapCustomers = null;
	private HazelcastInstance instance = null;
	private int HZ_WAIT_TIME = 10000;

	/**
	 * Method to setup the hazelcats instance
	 * @throws InterruptedException exception
	 * @throws java.lang.Exception
	 *             .
	 */
	@Before
	public void setUp() throws InterruptedException {
		final int three = 3;
		final Config cfg = new Config();
		instance = HazelcastInstanceFactory.newHazelcastInstance(cfg);
		while (!instance.getLifecycleService().isRunning()) {
			Thread.sleep(HZ_WAIT_TIME);
		}
		mapCustomers = instance.getMap("customers");
		mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(three, "Avi");
		// MockitoAnnotations.initMocks(this);
	}

	/**
	 * Method to test etNodeMemoryInfo.
	 * Testing if the entries set in map are same as expected
	 * @throws IOException IO exception
	 * @throws ReflectionException Reflection Exception
	 * @throws MBeanException MBeanException
	 * @throws MalformedObjectNameException Malformed Object
	 * @throws InstanceNotFoundException Instance NotFound
	 * @throws AttributeNotFoundException Attribute NotFound
	 */
	@Test
    public void testGetNodeMemoryInfo() throws
       IOException, AttributeNotFoundException, InstanceNotFoundException,
       MalformedObjectNameException, MBeanException, ReflectionException {

		Mockito.when(cacheInstance.getConnectionForHost(anyString()))
			.thenReturn(jmxConnector);
		Mockito.when(jmxConnector.getMBeanServerConnection())
		    .thenReturn(mBeanServerConnection);
		Mockito.when(mBeanServerConnection.getAttribute(
				new ObjectName(anyString()), anyString())).thenReturn(cd);
		Mockito.when(cd.get("used")).thenReturn(2000000L);
		Mockito.when(cd.get("committed")).thenReturn(3000000L);
		Mockito.when(cd.get("max")).thenReturn(4000000L);
		final String memoryMapExpected = "{\"HOST_NAME\":\"localhost\","
				+ "\"USED_MEMORY\":\"1\","
				+ "\"COMMITTED_MEMORY\":\"2\","
				+ "\"TOTAL_HEAP_MEMORY\":\"3\"}";
		final String memoryMapAsString =
				jMXService.getNodeMemoryInfo("localhost", "5701");
		assertEquals(memoryMapExpected, memoryMapAsString);
	}


	/**
	 * Method to test getNodeMemoryInfo for failure
	 * In this method we are not stubbing JMX call as a result
	 * method should fail with an exception string being returned
	 */
	@Test
    public void testGetNodeMemoryInfoFailure() {

		final String expectedErrorString =
				"Exception occurred when retriving memory info";
		final String actualErrorString = jMXService
				.getNodeMemoryInfo("localhost", "5701");
		assertEquals(expectedErrorString, actualErrorString);
	}

	/**
	 * Test case for getMapMemoryStats method of JMXService.
	 * Testing if the entries set in map are same as expected
	 * @throws IOException IO exception
	 * @throws ReflectionException Reflection Exception
	 * @throws MBeanException MBeanException
	 * @throws MalformedObjectNameException Malformed Object
	 * @throws InstanceNotFoundException Instance NotFound
	 * @throws AttributeNotFoundException Attribute NotFound
	 */
	@Test
	public void testGetMapMemoryStats() throws IOException,
		AttributeNotFoundException, InstanceNotFoundException,
		MalformedObjectNameException, MBeanException, ReflectionException {
		Mockito.when(cacheInstance.getConnectionForHost(anyString()))
			.thenReturn(jmxConnector);
		Mockito.when(jmxConnector.getMBeanServerConnection())
	    	.thenReturn(mBeanServerConnection);
		Mockito.when(mBeanServerConnection.getAttribute(
			new ObjectName(anyString()), anyString())).thenReturn(1L);
		final String expectedMapMemoryStats = "{\"HOST\":\"localhost\","
				+ "\"ENTRIES\":\"1\",\"BACKUPS\":\"1\","
				+ "\"ENTRY_MEMORY\":\"1\",\"BACKUP_MEMORY\":\"1\"}";
		final String actualMapMemoryStats =
				jMXService.getMapMemoryStats("localhost", "5701", "testMap");
		assertEquals(expectedMapMemoryStats, actualMapMemoryStats);
	}

	/**
	 * Method to shutdown the hazelcats instance
	 * @throws java.lang.Exception .
	 */
	@After
	public void cleanup() throws Exception {
		if (instance != null) {
	  		instance.shutdown();
	  	}
	}
}
