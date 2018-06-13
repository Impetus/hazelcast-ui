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
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.ui.controller.JmxController;

/**
 * Class to unit test the methods of mxController.
 *
 * @author sameena.parveen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-servlet.xml")
public class JmxControllerTest {
	private HazelcastInstance instance = null;
	private Map<String, String> mapCustomers = null;
	private int HZ_WAIT_TIME = 10000;
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
		final JmxControllerTest jmxObj = new JmxControllerTest();
		jmxObj.initialize(cfg);

		//objectMapper = new ObjectMapper();
	}

	/**
	 * This method checks if HZ instance is up and running before populating map.
	 * @param cfg config
	 * @throws InterruptedException exception
	 */
	public void initialize(final Config cfg) throws InterruptedException {
		instance = HazelcastInstanceFactory.newHazelcastInstance(cfg);
		while (!instance.getLifecycleService().isRunning()) {
			Thread.sleep(HZ_WAIT_TIME);
		}
		mapCustomers = instance.getMap("customers");
		mapCustomers.put("1", "Joe");
		mapCustomers.put("2", "Ali");
		mapCustomers.put("3", "Avi");
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
		final JmxControllerTest jmxObj = new JmxControllerTest();
		jmxObj.shutdownCurrentInstance();

	}

	/**
	 * This method shuts down HZ instance spawned by current test case.
	 */
	public void shutdownCurrentInstance() {
		if (instance != null) {
			instance.shutdown();
		}
	}
}
