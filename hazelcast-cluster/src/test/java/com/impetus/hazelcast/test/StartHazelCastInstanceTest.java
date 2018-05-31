package com.impetus.hazelcast.test;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.chainsaw.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.hazelcast.StartHazelCastInstance;

/**
 * Class to unit test the methods of StartHazelCastInstance.
 * 
 * @author sameena.parveen
 *
 */
public class StartHazelCastInstanceTest {
  
  /**
   * Set up method.
   * @throws java.lang.Exception exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * Test method for hazelcast instance check.
   */
  @Test
  public void testStartHazelCastInstance() {
    assertEquals(true, StartHazelCastInstance.startHazelCastInstance());
  }
  
  /**
   * Test main method for hazelcast instance check.
   */
  @Test
  public void testMain() {
      String[] args = null;
      StartHazelCastInstance.main(args);
  }
  /**
   * Clean up method.
   * @throws Exception exception
   */
  @After
  public void cleanup() throws Exception {
  }
}
