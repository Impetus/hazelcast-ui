package com.hazelcast.ui.service.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.ui.service.HazelcastRestService;
import com.hazelcast.util.CacheInstance;

/**
 * Class to unit test the methods of HazelCastRestService
 * @author sameena.parveen
 */

@RunWith(MockitoJUnitRunner.class)
public class HazelCastRestServiceTest {
	private HazelcastInstance instance = null;
	@InjectMocks
	private HazelcastRestService hazelcatRestService =
			new HazelcastRestService();
	@Mock
	private CacheInstance cacheInstance;
	@Mock
	private ObjectMapper objectMapper;
	@Mock
	private SimpleDateFormat sdf;

	// @Mock
	// private SimpleDateFormat sdf;
	private static Map<Integer , String> mapCustomers = null;
	private int HZ_WAIT_TIME = 10000;

	/**
	 * Method to setup the hazelcats instance
	 * @throws java.lang.Exception .
	 */
	@Before
	public void setUp() throws Exception {
		final int three = 3;
		final Config cfg = new Config();
		cfg.getNetworkConfig().setPublicAddress("localhost:5701");
		instance = HazelcastInstanceFactory.newHazelcastInstance(cfg);
		while (!instance.getLifecycleService().isRunning()) {
			Thread.sleep(HZ_WAIT_TIME);
		}
		mapCustomers = instance.getMap("customers");
		mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(three, "Avi");
		MockitoAnnotations.initMocks(this);
	}
	/**
	 * Method to test the getKey method
	 */
	@Test
	public void testGetKey() {
		final int lack = 100000;
		final long tenLack = 10000000L;
		assertEquals("1", hazelcatRestService.getKey("String", "1"));
		assertEquals(lack, hazelcatRestService.getKey("Integer", "100000"));
		assertEquals(tenLack, hazelcatRestService.getKey("Long", "10000000"));

	}
	/**
	 * Method to test the getMembersInfo method
	 * @throws JsonProcessingException .
	 */
	@Test
	public void testGetMembersInfo() throws JsonProcessingException {
		final List<String> members = new ArrayList<>();
		members.add("localhost" + ":" + "5701");
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		Mockito.when(objectMapper.writeValueAsString(members))
				.thenReturn(members.get(0));
		assertEquals("localhost:5701", hazelcatRestService.getMembersInfo());

	}

	/**
	 * Method to test the getMapsName method
	 * @throws com.fasterxml.jackson.core.JsonProcessingException .
	 */
	@Test
	public void testGetMapsName() throws JsonProcessingException {
		final List<String> maps = new ArrayList<>();
		maps.add("customers");
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		Mockito.when(objectMapper.writeValueAsString(maps))
				.thenReturn(maps.get(0));
		assertEquals("customers", hazelcatRestService.getMapsName());
	}

	/**
	 * Method to test the getSize method
	 * @throws com.fasterxml.jackson.core.JsonProcessingException .
	 */
	@Test
	public void testGetSize() throws JsonProcessingException {
		final int three = 3;
		final Map<String, Integer> sizeMap = new HashMap<>();
		sizeMap.put("Size", three);
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		Mockito.when(objectMapper.writeValueAsString(sizeMap)).thenReturn("3");
		assertEquals("3",
				hazelcatRestService.getSize("customers"));
	}

	/**
	 * Method to check for failure of get size method.
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSizeFailureJSONException() throws JsonProcessingException {
		final int three = 3;
		final Map<String, Integer> sizeMap = new HashMap<>();
		sizeMap.put("Size", three);
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		Mockito.when(objectMapper.writeValueAsString(sizeMap)).
			thenThrow(JsonProcessingException.class);
		assertEquals("Exception occurred while "
				+ "fecthing entrycom.fasterxml.jackson.core.JsonProcessingException: "
				+ "N/A", hazelcatRestService.getSize("customers"));
	}

	/**
	 * Method to check for failure of get size method.
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetValueFromMapFailureParseException() {
		final int three = 3;
		final Map<String, Integer> sizeMap = new HashMap<>();
		sizeMap.put("Size", three);
		final String result = hazelcatRestService.
				getValueFromMap("testMap", "14", "Double");
		assertEquals("{\"Value\":\"No Value Found\"}", result);

	}


	/**
	 * Method to test getKey method for String input
	 */
	@Test
	public void testGetKeyString() {
		final Object result = hazelcatRestService.getKey("String", "testKey");
		assertEquals("testKey", result);
	}

	/**
	 * Method to test getKey method for integer input
	 */
	@Test
	public void testGetKeyInteger() {
		final Object result = hazelcatRestService.getKey("Integer", "2");
		assertEquals(2, result);
	}

	/**
	 * Method to test getKey method for long input
	 */
	@Test
	public void testGetKeyLong() {
		final Object result = hazelcatRestService.getKey("Long", "2");
		assertEquals(2L, result);
	}

	/**
	 * Method to test the shutdown hazelcast
	 * @throws java.lang.Exception .
	 */
	@After
	public void cleanup() throws Exception {
		if (instance != null) {
	  		instance.shutdown();
	  	}
	}

}
