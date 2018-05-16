package com.impetus.hazelcast.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.core.Hazelcast;
import com.impetus.hazelcast.StartHazelCastInstance;

public class StartHazelCastInstanceTests {
	private StartHazelCastInstance startHazelCast;
	@Before
	public void setUp() throws Exception {
		
		startHazelCast=new StartHazelCastInstance();
	}

	@Test
	public void testStartHazelCastInstance() {
		assertEquals(true, startHazelCast.startHazelCastInstance());
	}
	@After
	public void cleanup() throws Exception {
	  Hazelcast.shutdownAll();
	}
}
