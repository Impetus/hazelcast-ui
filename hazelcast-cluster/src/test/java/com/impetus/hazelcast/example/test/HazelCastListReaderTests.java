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
import com.impetus.hazelcast.example.HazelCastListReader;
import com.impetus.hazelcast.example.HazelcastMapLoader;

public class HazelCastListReaderTests {
	Config cfg = new Config();
    static HazelcastInstance instance=null;
    static Map<Integer, String> mapCustomers=null;
    static HazelCastListLoader hazelcastListLoader;
    static HazelCastListReader hazelcastListReader;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Config cfg = new Config();
   	   instance = Hazelcast.newHazelcastInstance(cfg);
		HazelcastMapLoader.loadHazelCastMap();
		hazelcastListLoader=new HazelCastListLoader();
		hazelcastListReader=new HazelCastListReader();
	}

	@Test
	public void testLoadHazelCastList() {
		
		hazelcastListLoader.loadHazelCastList();
		assertEquals(1000, hazelcastListReader.readList().size());
	}
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
}
