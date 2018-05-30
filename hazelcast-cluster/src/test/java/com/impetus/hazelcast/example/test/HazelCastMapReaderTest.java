package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.HazelCastMapReader;
import com.impetus.hazelcast.example.HazelcastMapLoader;

/**
 * Class to unit test the methods of HazelCastMapReader.
 * 
 * @author sameena.parveen
 *
 */
public class HazelCastMapReaderTest {
  static HazelcastInstance instance = null;
  static HazelcastMapLoader hazelcastMapLoader;
  static HazelCastMapReader hazelcastMapReader;
  
  /**
   * Set up method.
   * @throws java.lang.Exception exception
   */
  @Before
  public void setUp() throws Exception {
    
    Config cfg = new Config();
    instance = Hazelcast.newHazelcastInstance(cfg);
    HazelcastMapLoader.loadHazelCastMap();
    hazelcastMapLoader = new HazelcastMapLoader();
    hazelcastMapReader = new HazelCastMapReader();
  }
  
  /**
   * Test method to check hazelcast map loading.
   */
  @Test
  public void testLoadHazelCastList() {
    
    HazelcastMapLoader.loadHazelCastMap();
    assertEquals(1000, hazelcastMapReader.readMap().size());
  }
  
  /**
   * Clean up method.
   * @throws Exception exception
   */
  @After
  public void cleanup() throws Exception {
    instance.shutdown();
  }
}
