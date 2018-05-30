package com.hazelcast.ui.service.test;

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
import com.hazelcast.ui.service.JMXService;
import com.hazelcast.util.CacheInstance;
@RunWith(MockitoJUnitRunner.class)
public class JMXServiceTest {
	@InjectMocks
	private JMXService  jMXService=new JMXService();
	@Mock
    private CacheInstance cacheInstance;
	static Map<Integer, String> mapCustomers=null;
	private HazelcastInstance instance=null;
	@Before
	public  void setUp()  {
		//jMXService=new JMXService();
		Config cfg = new Config();
	   	   instance = Hazelcast.newHazelcastInstance(cfg);
	   	mapCustomers = instance.getMap("customers");
	    mapCustomers.put(1, "Joe");
	    mapCustomers.put(2, "Ali");
	    mapCustomers.put(3, "Avi");
	    //MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void TestGetNodeMemoryInfo(){
		//System.out.println("Hii"+jMXService.getNodeMemoryInfo("192.168.0.11", "5702"));
		
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
}
