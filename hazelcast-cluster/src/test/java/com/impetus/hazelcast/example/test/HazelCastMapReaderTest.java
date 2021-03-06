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
 * @author sameena.parveen
 */
public class HazelCastMapReaderTest {
	private static HazelcastInstance instance = null;
	private static HazelCastMapReader hazelcastMapReader;

	/**
	 * Set up method.
	 * @throws java.lang.Exception
	 *             exception
	 */
	@Before
	public void setUp() throws Exception {

		final Config cfg = new Config();
		instance = Hazelcast.newHazelcastInstance(cfg);
		HazelcastMapLoader.loadHazelCastMap();
		hazelcastMapReader = new HazelCastMapReader();
	}

	/**
	 * Test method to check hazelcast map loading.
	 */
	@Test
	public void testLoadHazelCastList() {
		final int thousand = 1000;
		HazelcastMapLoader.loadHazelCastMap();
		assertEquals(thousand, hazelcastMapReader.readMap().size());
	}

	/**
	 * Test main method for HazelCastMapReader instance check.
	 */
	@Test
	public void testMain() {
		final String[] args = null;
		HazelCastMapReader.main(args);
	}

	/**
	 * Clean up method.
	 * @throws Exception
	 *             exception
	 */
	@After
	public void cleanup() throws Exception {
		instance.shutdown();
	}
}
