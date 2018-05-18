package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.HazelCastListLoader;
import com.impetus.hazelcast.example.HazelcastMapLoader;

/**Class to unit test the methods of HazelCastListLoader
 * 
 * @author sameena.parveen
 *
 */
public class HazelCastListLoaderTests {
	Config cfg = new Config();
    static HazelcastInstance instance=null;
    static Map<Integer, String> mapCustomers=null;
    static HazelCastListLoader hazelcastListLoader;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Config cfg = new Config();
   	   instance = Hazelcast.newHazelcastInstance(cfg);
		HazelcastMapLoader.loadHazelCastMap();
		hazelcastListLoader=new HazelCastListLoader();
	}

	@Test
	public void testLoadHazelCastList() {
		
		assertEquals(1000, hazelcastListLoader.loadHazelCastList());
	}
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
}