package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.HazelCastMapReader;
import com.impetus.hazelcast.example.HazelcastMapLoader;

public class HazelCastMapReaderTests {
	Config cfg = new Config();
    static HazelcastInstance instance=null;
    static Map<Integer, String> mapCustomers=null;
    static HazelcastMapLoader hazelcastMapLoader;
    static HazelCastMapReader hazelcastMapReader;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Config cfg = new Config();
   	   instance = Hazelcast.newHazelcastInstance(cfg);
		HazelcastMapLoader.loadHazelCastMap();
		hazelcastMapLoader=new HazelcastMapLoader();
		hazelcastMapReader=new HazelCastMapReader();
	}

	@Test
	public void testLoadHazelCastList() {
		
		HazelcastMapLoader.loadHazelCastMap();
		assertEquals(1000, hazelcastMapReader.readMap().size());
	}
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
}
