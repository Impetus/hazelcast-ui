/**
 * Name           : Reader.java
 * Type           : JAVA
 * Purpose        : This class loads hazelcast property file
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.impetus.hazelcast.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.impetus.hazelcast.LogUtils;
import com.impetus.hazelcast.exception.HazelcastStartUpException;

@SuppressWarnings("unchecked")
public class Reader {
	private static final String COMMA_SEPARATOR = ",";
	private static final Logger LOGGER = LogUtils.getLogger(Reader.class);
	//// Property file should be added in class path in case the user is running
	//// the code from eclipse.
	private static final String HAZELCAST_SERVER_PROP =
			"hazelcast-server.properties";
	private static final String CACHE_SERVER = "cache.server";

	private ObjectMapper mapper = null;
	private ObjectWriter objectWriter = null;
	private HazelcastInstance client = null;
	private IMap<String, String> testMap = null;
	private IList<String> testList = null;

	/**
	 * Constructor method.
	 */
	public Reader() {
		mapper = new ObjectMapper();
		objectWriter = mapper.writer();
		initializeReader(CACHE_SERVER);
	}

	/**
	 * Parameterized Constructor method.
	 * @param cacheServer .
	 */
	public Reader(final String cacheServer) {
		mapper = new ObjectMapper();
		objectWriter = mapper.writer();
		initializeReader(cacheServer);
	}

	/**
	 * This method is used to return instance of hazelcast client.
	 * @return Hazelcast client .
	 */
	public HazelcastInstance getHazelCastClient() {
		return client;
	}

	/**
	 * This method is used to shutdown hazelcast client instance.
	 */
	public void shutdown() {
		client.getLifecycleService().shutdown();
	}

	/**
	 * This method is used to load property file.
	 * @param inputStream .
	 * @return Properties Oject .
	 */
	public static Properties readPropertyFile(final InputStream inputStream) {

		LOGGER.debug("readPropertyFile method :: Start ");

		final Properties properties = new Properties();

		try {
			properties.load(inputStream);

			if (properties.size() == 0) {
				LOGGER.error("Error reading configuration file. "
						+ "File {} does not contain any property",
						HAZELCAST_SERVER_PROP);
			}

		} catch (final FileNotFoundException e) {
			LOGGER.error("Error reading configuration file. File "
					+ HAZELCAST_SERVER_PROP + " does not exist", e);
		} catch (final IOException e) {
			LOGGER.error("Error reading configuration file : "
					+ HAZELCAST_SERVER_PROP, e);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					LOGGER.error(
							"Error closing input stream in readPropertyFile()",
							e);
				}
			}
		}

		LOGGER.debug("readPropertyFile method :: End ");

		return properties;
	}

	/**
	 * Loads hazelcast server properties from config file.
	 * @return cachingProps .
	 */
	public Properties getCachingConfig() {
		Properties cachingProps = null;
		final InputStream hazelcastServerProp = this.getClass().getClassLoader()
				.getResourceAsStream(HAZELCAST_SERVER_PROP);
		try {
			cachingProps = readPropertyFile(
					hazelcastServerProp);
		} catch (final Exception e) {
			LOGGER.error("Error while reading configuration file", e);
		}
		return cachingProps;

	}

	/**
	 * This method is used to initialize reader instance which inturn
	 * initializes hazelcast client.
	 * @return boolean value. True when the Properies are read successfully
	 * false in cas eof Exception/Error.
	 * @param cacheServer .
	 **/
	public boolean initializeReader(final String cacheServer) {

		Properties cacheProps = null;
		final ClientConfig clientConfig = new ClientConfig();
		String strClients = null;
		boolean isStarted = false;

		try {
			cacheProps = getCachingConfig();
		} catch (final Exception e) {
			LOGGER.error("Error reading resources", e);
			throw new HazelcastStartUpException(
					"Error reading resources " + e.getMessage());
		}

		if (cacheProps != null) {
			LOGGER.info("Loaded hazelcast properties from property file");

			// Fetch list of hazel cast nodes
			// configured using key cache.server in hazelcast-server.properties
			strClients = cacheProps.getProperty(cacheServer);

			if (null == strClients) {
				LOGGER.error(
						"Unable to fetch cache server addresses from config file ");
				throw new HazelcastStartUpException(
						"Could not connect to cache server server..No IPs configured");
			}

			LOGGER.info("Established connection with cache server {}",
					strClients);

			final String[] addrSplits = strClients.split(COMMA_SEPARATOR);
			for (String addr : addrSplits) {
				clientConfig.getNetworkConfig().addAddress(addr);
			}

			// Instantiating hazelcast client
			client = HazelcastClient.newHazelcastClient(clientConfig);
			LOGGER.info("Serializers set");
			if (null == client) {
				LOGGER.error("Could not instantiate client");
				throw new HazelcastStartUpException(
						"Could not instantiate client");
			}
			isStarted = true;
			final long t1 = System.currentTimeMillis();
			LOGGER.info("Hazelcast client instantiated successfully. "
					+ "Time taken to initiate cache client is {} msec",
					(System.currentTimeMillis() - t1));
		} else {
			LOGGER.error(
					"Unable to fetch cache server addresses from config file ");
			throw new HazelcastStartUpException(
					"Could not connect to cache server server"
							+ "..Not able to load property file");
		}
		return isStarted;
	}

	/**
	 * This method is used to initialize test Map.
	 * @return testMap .
	 */
	public IMap<String, String> initializeTestMap() {

		try {
			testMap = client.getMap("testMap");
			LOGGER.info("Loaded sample map. Size of map is {}", testMap.size());
		} catch (final Exception e) {
			LOGGER.error("Error while fetching testMap from cache server. ", e);
			testMap = (IMap<String, String>) Collections.EMPTY_MAP;
		}
		return testMap;
	}

	/**
	 * This method is used to initialize test List.
	 * @return testList .
	 */
	public IList<String> initializeTestList() {

		try {
			testList = client.getList("testList");

			LOGGER.info("Loaded sample list. Size of list is {}",
					testList.size());
		} catch (final Exception e) {
			LOGGER.error("Error while fetching testList from cache server. ",
					e);
			testList = (IList<String>) Collections.EMPTY_LIST;
		}
		return testList;
	}

	/**
	 * This method is used to get test Map.
	 * @return testMap .
	 */
	public IMap<String, String> getTestMap() {
		return testMap;
	}

	/**
	 * This method is used to get test list.
	 * @return testList .
	 */
	public IList<String> getTestList() {
		return testList;
	}

}
