package com.hazelcast.ui.controller.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ui.controller.HazelcastRestController;
import com.hazelcast.ui.service.HazelcastRestService;


/**
 * Class to unit test the methods of HazelcastRestController.
 * 
 * @author sameena.parveen
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HazelcastRestControllerTest {
  Config cfg = new Config();
  static HazelcastInstance instance = null;
  static Map<String, String> mapCustomers = null;
  
  @InjectMocks
  private HazelcastRestController hazecastRestController = new HazelcastRestController();
  @Mock
  private HazelcastRestService hazelcastRestService;
  
  private ObjectMapper objectMapper;
  /**
   * Set up method.
   * @throws java.lang.Exception exception 
   */
  @Before
  public  void setUp() throws Exception {
    
    Config cfg = new Config();
    instance = Hazelcast.newHazelcastInstance(cfg);
    mapCustomers = instance.getMap("customers");
    mapCustomers.put("1", "Joe");
    mapCustomers.put("2", "Ali");
    mapCustomers.put("3", "Avi");
    objectMapper = new ObjectMapper();
  }
  
  @Test
  public void testWelcome() {
    HazelcastRestController hazelcastRestController = new HazelcastRestController();
    assertEquals("Welcome to Hazelcast Web UI", hazelcastRestController.welcome());
  }
  
  
  @Test
  public void testGetMembersInfo() throws JsonProcessingException {
    Mockito.when(hazelcastRestService.getMembersInfo()).thenReturn("localhost:5701");
    assertEquals("localhost:5701",hazecastRestController.getClusterInfo());
  }
  
  @Test
  public void testGetMapsName() {
    Mockito.when(hazelcastRestService.getMapsName()).thenReturn("customers");
    assertEquals("customers", hazecastRestController.getMapsName());
  }
  
  @Test
  public void testGetSize() {
    Mockito.when(hazelcastRestService.getSize("customers")).thenReturn("3");
    assertEquals("3", hazecastRestController.getSize("customers"));
  }
  
  /**
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 */
    @Test
	public void testGetValue() throws JsonProcessingException {
    Map<String, Object> entryMap = new HashMap<>();
    entryMap.put("Value","Value1");
	entryMap.put("Creation_Time", "2018-05-18 21:32:14.993");
	entryMap.put("Last_Update_Time", "2018-05-18 21:32:14.993");
	entryMap.put("Last_Access_Time", "2018-05-18 21:32:52.883");
	entryMap.put("Expiration_Time", "292278994-08-17 12:42:55.807");
	String entryMapString= objectMapper.writeValueAsString(entryMap);
    Mockito.when(hazelcastRestService.getValueFromMap("testMap", "Key1", "String")).thenReturn(entryMapString);
    assertEquals(entryMapString, hazecastRestController.getValue("testMap", "Key1", "String"));
  }

  @After
  public void cleanup() throws Exception {
    Hazelcast.shutdownAll();  
  }
}
