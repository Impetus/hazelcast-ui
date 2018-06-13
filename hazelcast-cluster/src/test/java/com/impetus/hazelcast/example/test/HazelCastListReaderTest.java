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
 * @author sameena.parveen
 */
public class HazelCastListReaderTest {
	private static HazelcastInstance instance = null;
	private static HazelCastListLoader hazelcastListLoader;
	private static HazelCastListReader hazelcastListReader;

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
		hazelcastListLoader = new HazelCastListLoader();
		hazelcastListReader = new HazelCastListReader();
	}

	/**
	 * Method to test loadHazelCastList method.
	 * @throws java.lang.Exception
	 *             exception
	 */
	@Test
	public void testLoadHazelCastList() {
		final int thousand = 1000;
		hazelcastListLoader.loadHazelCastList();
		assertEquals(thousand, hazelcastListReader.readList().size());
	}

	/**
	 * Test main method for HazelCastListReader instance check.
	 */
	@Test
	public void testMain() {
		final String[] args = null;
		HazelCastListReader.main(args);
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
