package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.HazelcastMapLoader;

/**Class to unit test the methods of HazelcastMapLoader
 * 
 * @author sameena.parveen
 *
 */
public class HazelcastMapLoaderTests {
	Config cfg = new Config();
    static HazelcastInstance instance=null;
    static Map<Integer, String> mapCustomers=null;
    static HazelcastMapLoader hazelcastMapLoader;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Config cfg = new Config();
   	   instance = Hazelcast.newHazelcastInstance(cfg);
		HazelcastMapLoader.loadHazelCastMap();
		hazelcastMapLoader=new HazelcastMapLoader();
	}

	@Test
	public void testLoadHazelCastList() {
		
		assertEquals(1000, hazelcastMapLoader.loadHazelCastMap());
	}
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
}
