package com.hazelcast.ui.controller.test;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ui.controller.HazelcastRestController;
import com.hazelcast.ui.service.HazelcastRestService;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Class to unit test the methods of HazelcastRestController.
 * 
 * @author sameena.parveen
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HazelcastRestControllerTests {
  Config cfg = new Config();
  static HazelcastInstance instance = null;
  static Map<Integer, String> mapCustomers = null;
  
  @InjectMocks
  private HazelcastRestController hazecastRestController = new HazelcastRestController();
  @Mock
  private HazelcastRestService hazelcastRestService;
  
  /*@Mock
  private CacheInstance cacheInstance;
  */
  
  /**
   * Set up method.
   * @throws java.lang.Exception exception 
   */
  @Before
  public  void setUp() throws Exception {
    
    Config cfg = new Config();
    instance = Hazelcast.newHazelcastInstance(cfg);
    mapCustomers = instance.getMap("customers");
    mapCustomers.put(1, "Joe");
    mapCustomers.put(2, "Ali");
    mapCustomers.put(3, "Avi");
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
    assertEquals("customers", hazelcastRestService.getMapsName());
  }
  
  @Test
  public void testGetSize() {
    Mockito.when(hazelcastRestService.getSize("customers")).thenReturn("3");
    assertEquals("3", hazelcastRestService.getSize("customers"));
  }
  
  /*@Test
  public void testGetValue() {
    Mockito.when(hazelcastRestService.getValueFromMap("customers", "1", "String")).thenReturn("3");
    assertEquals("3", hazelcastRestService.getSize("customers"));
}*/

  @After
  public void cleanup() throws Exception {
    Hazelcast.shutdownAll();  
  }
}
