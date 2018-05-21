package com.impetus.hazelcast.test;

import static org.junit.Assert.assertEquals;

import com.hazelcast.core.Hazelcast;
import com.impetus.hazelcast.StartHazelCastInstance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to unit test the methods of StartHazelCastInstance.
 * 
 * @author sameena.parveen
 *
 */
public class StartHazelCastInstanceTests {
  
  private StartHazelCastInstance startHazelCast;
  
  /**
   * Set up method.
   * @throws java.lang.Exception exception
   */
  @Before
  public void setUp() throws Exception {
    
    startHazelCast = new StartHazelCastInstance();
  }

  /**
   * Test method for hazelcast instance check.
   */
  @Test
  public void testStartHazelCastInstance() {
    assertEquals(true, startHazelCast.startHazelCastInstance());
  }
  
  /**
   * Clean up method.
   * @throws Exception exception
   */
  @After
  public void cleanup() throws Exception {
    Hazelcast.shutdownAll();
  }
}
