package com.impetus.hazelcast.example.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.impetus.hazelcast.example.HazelCastListLoader;
import com.impetus.hazelcast.example.HazelcastMapLoader;

/**
 * Class to unit test the methods of HazelCastListLoader.
 * @author sameena.parveen
 */
public class HazelCastListLoaderTest {
	private static HazelcastInstance instance = null;
	private static HazelCastListLoader hazelcastListLoader;

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
	}

	/**
	 * Method to test loadHazelCastList method.
	 * @throws java.lang.Exception
	 *             exception
	 */
	@Test
	public void testLoadHazelCastList() {
		final int threeThousand = 3000;
		assertEquals(threeThousand, hazelcastListLoader.loadHazelCastList());
	}

	/**
	 * Test main method for HazelCastListLoader instance check.
	 */
	@Test
	public void testMain() {
		final String[] args = null;
		HazelCastListLoader.main(args);
	}

	/**
	 * Cleanup method.
	 * @throws Exception
	 *             .
	 */
	@After
	public void cleanup() throws Exception {
		instance.shutdown();
	}
}
