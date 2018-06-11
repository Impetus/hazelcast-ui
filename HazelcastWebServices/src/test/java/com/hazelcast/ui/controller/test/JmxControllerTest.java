package com.hazelcast.ui.controller.test;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ui.controller.JmxController;

/**
 * Class to unit test the methods of mxController.
 *
 * @author sameena.parveen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-servlet.xml")
public class JmxControllerTest {
	private static HazelcastInstance instance = null;
	private static Map<String, String> mapCustomers = null;

	@Autowired
	private JmxController jmxController;

	//private static ObjectMapper objectMapper;

	/**
	 * Set up method.
	 *
	 * @throws java.lang.Exception
	 *             exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("DEPLOY_ENV", "local");
		final Config cfg = new Config();
		cfg.getNetworkConfig().setPublicAddress("localhost:5701");
		cfg.setProperty("hazelcast.jmx", "true");
		cfg.setProperty("hazelcast.jmxremote.port", "1010");
		cfg.setProperty("hazelcast.jmxremote.authenticate", "false");
		cfg.setProperty("hazelcast.jmxremote.ssl", "false");
		cfg.setProperty("hazelcast.jmxremote.ssl", "false");
		instance = Hazelcast.newHazelcastInstance(cfg);
		mapCustomers = instance.getMap("customers");
		mapCustomers.put("1", "Joe");
		mapCustomers.put("2", "Ali");
		mapCustomers.put("3", "Avi");
		//objectMapper = new ObjectMapper();
	}
	/**
	   * This test case is to test method getMemoryInfo
	   */
	@Test
	public void getMemoryInfo() {
		jmxController.getMemoryInfo("localhost", "1010");

	}
	/**
	   * This test case is to test method getMapMemoryStats
	   */
	@Test
	public void getMapMemoryStats() {
		jmxController.getMapMemoryStats("localhost", "1010", "customers");

	}
	/**
	   * This test case is to shutdown hazelcast
	   * @throws Exception .
	   */
	@AfterClass
	public static void cleanup() throws Exception {
		Hazelcast.shutdownAll();
	}
}
