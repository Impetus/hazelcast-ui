package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.Reader;

public class ReaderTests {
public Reader reader;
static HazelcastInstance instance=null;

	@Before
	public void setUp() throws Exception {
		Config cfg = new Config();
	   	   instance = Hazelcast.newHazelcastInstance(cfg);
	   	   reader=new Reader();
	}
	
	@Test
	public void TestReadPropertyFile(){
	assertEquals("{cache.server=localhost:5701}", Reader.getCachingConfig().toString());
	}
	
	@Test
	public void TestGetCachingConfig(){
	assertEquals("{cache.server=localhost:5701}", Reader.getCachingConfig().toString());
	}
	
	@Test
	public void TestetInitializeReader(){
		assertEquals(true,reader.initializeReader("cache.server"));
	}
	
	@Test
	public void TestetIinitializeTestMap(){
		assertEquals("testMap",reader.initializeTestMap().getName());
	}
	
	@Test
	public void TestetIinitializeTestList(){
		assertEquals("testList",reader.initializeTestList().getName());
	}
	
	
}
