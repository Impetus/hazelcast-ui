package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.Reader;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Reader class.
 * @author pushkin.gupta
 *
 */
public class ReaderTest {
  public Reader reader;
  static HazelcastInstance instance = null;

  /**
   * Set up method.
   * @throws Exception exception
   */
  @Before
  public void setUp() throws Exception {
    Config cfg = new Config();
    instance = Hazelcast.newHazelcastInstance(cfg);
    reader = new Reader();
  }
  
  /**
   * Test method to test property file.
   */
  @Test
  public void testReadPropertyFile() {
    assertEquals("{cache.server=localhost:5701}",
    		reader.getCachingConfig().toString());
  }
  
  /**
   * Test method to test get cache config.
   */
  @Test
  public void testGetCachingConfig() {
    assertEquals("{cache.server=localhost:5701}", 
    		reader.getCachingConfig().toString());
  }
  
  /**
   * Test method to  verify reader.
   */
  @Test
  public void testetInitializeReader() {
    assertEquals(true,reader.initializeReader("cache.server"));
  }
  
  /**
   * Test method to  initialize map.
   */
  @Test
  public void testetIinitializeTestMap() {
    assertEquals("testMap",reader.initializeTestMap().getName());
  }
  
  /**
   * Test method to  initialize list.
   */
  @Test
  public void testetIinitializeTestList() {
    assertEquals("testList",reader.initializeTestList().getName());
  }
  
  /**
   * Cleanup method.
   */
  @After
  public void cleanup() throws Exception {
    Hazelcast.shutdownAll();
  }
  
}
