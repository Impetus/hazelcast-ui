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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ui.service.HazelcastRestService;
import com.hazelcast.util.CacheInstance;

/**Class to unit test the methods of HazelCastRestService
 * 
 * @author sameena.parveen
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class HazelCastRestServiceTest {
    private HazelcastInstance instance=null;
    @InjectMocks
    private HazelcastRestService hazelcatRestService = new HazelcastRestService();
    @Mock
    private CacheInstance cacheInstance;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private SimpleDateFormat sdf;
    
    //@Mock
   // private SimpleDateFormat sdf;
    static Map<Integer, String> mapCustomers=null;
    
    /**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Config cfg = new Config();
   	   instance = Hazelcast.newHazelcastInstance(cfg);
   	mapCustomers = instance.getMap("customers");
    mapCustomers.put(1, "Joe");
    mapCustomers.put(2, "Ali");
    mapCustomers.put(3, "Avi");
    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetKey() {
		
		assertEquals("1", hazelcatRestService.getKey("String", "1"));
		assertEquals(100000, hazelcatRestService.getKey("Integer", "100000"));
		assertEquals(10000000L, hazelcatRestService.getKey("Long", "10000000"));
		
	}
	
	/*@Test
	public void testGetMembersInfo() throws JsonProcessingException {
		List<String> members = new ArrayList<>();
		members.add("localhost" + ":" + "5701");
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		//Mockito.when(objectMapper.writeValueAsString(members)).thenReturn("localhost:5701");
		Mockito.when(objectMapper.writeValueAsString("localhost:5701")).thenReturn(members.toString());
		//objectMapper.writeValueAsString(members)
		assertEquals("localhost:5701",hazelcatRestService.getMembersInfo());
		
	}*/
	
	/**
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 */
	@Test
	public void testGetMapsName() throws JsonProcessingException {
		List<String> maps = new ArrayList<>();
		maps.add("customers");
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		Mockito.when(objectMapper.writeValueAsString(maps)).thenReturn(maps.get(0));
		assertEquals("customers",hazelcatRestService.getMapsName());
	}
	
	/**
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 */
	@Test
	public void testGetSize() throws JsonProcessingException {
		Map<String, Integer> sizeMap = new HashMap<>();
		sizeMap.put("Size",3);
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		Mockito.when(objectMapper.writeValueAsString(sizeMap)).thenReturn("3");
		assertEquals("3",hazelcatRestService.getSize("customers"));
	}
	/*@Test
	public void testGetValueFromMap() {
		//Calendar cal = Calendar.getInstance();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		SimpleDateFormat mock = org.mockito.Mockito.mock(SimpleDateFormat.class);
		Calendar cal = org.mockito.Mockito.mock(Calendar.class);
		Mockito.when(cal.getTime()).thenReturn(cal.getTime());
		Mockito.when(mock.format(cal)).thenReturn("2018-05-15 16:22:56.889");
		//when(mock.read("1")).thenReturn(pcUser);
		//Mockito.when(sdf.format(cal.getTime())).thenReturn("2018-05-15 16:22:56.889");
		hazelcatRestService.getValueFromMap("customers","1","Integer");
	}
	*/
	
	/*@Test
	public void testGetValueFromMap() {
		Mockito.when(cacheInstance.getClient()).thenReturn(instance);
		//SimpleDateFormat mock = org.mockito.Mockito.mock(SimpleDateFormat.class);
		Calendar cal = org.mockito.Mockito.mock(Calendar.class);
		//Calendar cal = Calendar.getInstance();
		//Mockito.when(cal.getTime()).thenReturn();
		Mockito.when(sdf.format(any(cal))).thenReturn("2018-05-15 16:22:56.889");
		hazelcatRestService.getValueFromMap("customers","1","Integer");
	}*/
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
	
	
}

