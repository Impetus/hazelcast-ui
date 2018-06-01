package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.HazelCastListLoader;
import com.impetus.hazelcast.example.HazelCastListReader;
import com.impetus.hazelcast.example.HazelcastMapLoader;

/**
 * Class to unit test the methods of HazelCastListReader.
 * 
 * @author sameena.parveen
 *
 */
public class HazelCastListReaderTest {
  static HazelcastInstance instance = null;
  static HazelCastListLoader hazelcastListLoader;
  static HazelCastListReader hazelcastListReader;
  
  /**
   * Set up method.
   * @throws java.lang.Exception exception
   */
  @Before
  public void setUp() throws Exception {
    
    Config cfg = new Config();
    instance = Hazelcast.newHazelcastInstance(cfg);
    HazelcastMapLoader.loadHazelCastMap();
    hazelcastListLoader = new HazelCastListLoader();
    hazelcastListReader = new HazelCastListReader();
  }

  @Test
  public void testLoadHazelCastList() {
   hazelcastListLoader.loadHazelCastList();
    assertEquals(1000, hazelcastListReader.readList().size());
  }
  /**
   * Test main method for HazelCastListReader instance check.
   */
  @Test
  public void testMain() {
      String[] args = null;
      HazelCastListReader.main(args);
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
