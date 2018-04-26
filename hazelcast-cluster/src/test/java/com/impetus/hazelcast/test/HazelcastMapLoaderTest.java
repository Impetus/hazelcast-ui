/**
 * 
 */
package com.impetus.hazelcast.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.impetus.hazelcast.HazelcastMapLoader;

/**
 * @author pushkin.gupta
 *
 */
public class HazelcastMapLoaderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		HazelcastMapLoader.loadHazelCastMap();
	}

	@Test
	public void test() {
		Map<String,String> testMap = HazelcastMapLoader.getTestMap();
		assertEquals(1000, testMap.size());;
	}

}
