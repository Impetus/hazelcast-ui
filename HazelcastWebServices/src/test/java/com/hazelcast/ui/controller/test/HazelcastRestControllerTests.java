package com.hazelcast.ui.controller.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ui.controller.HazelcastRestController;
import com.hazelcast.ui.service.HazelcastRestService;
import com.hazelcast.util.CacheInstance;

@RunWith(MockitoJUnitRunner.class)
public class HazelcastRestControllerTests {
	Config cfg = new Config();
    static HazelcastInstance instance=null;
    static Map<Integer, String> mapCustomers=null;
    
    @InjectMocks
    private HazelcastRestController myLauncher = new HazelcastRestController();
    @Mock
    private HazelcastRestService hazelcastRestService;
    @Mock
    private CacheInstance cacheInstance;
    
    
    
    
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
        HazelcastRestController hazelcastRestController=new HazelcastRestController();
        assertEquals("Welcome to Hazelcast Web UI", hazelcastRestController.welcome());
        
        
}
    /*@Test
	public void testGetClusterInfo() {
        HazelcastRestController s=new HazelcastRestController();
        System.out.println("The maps are:::"+s.getMapsName());
        
        //assertEquals("Welcome to Hazelcast Web UI", s.welcome());
        
        
}*/

@After
public void cleanup() throws Exception {
  Hazelcast.shutdownAll();
}
}
