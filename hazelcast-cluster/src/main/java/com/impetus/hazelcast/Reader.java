package com.impetus.hazelcast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@SuppressWarnings("unchecked")
public class Reader {
	private static final String COMMA_SEPARATOR = ",";

	private static final Logger logger = LogUtils.getLogger(Reader.class);
	
	ObjectMapper mapper = null;
	ObjectWriter objectWriter = null;

	HazelcastInstance client = null;


	IMap<String, String> testMap = null;
	
	/**
	 * Constructor method
	 */
	public Reader() {
		mapper = new ObjectMapper();
		objectWriter = mapper.writer();
		initializeReader("cache.server");
	}

	/**
	 * Parameterized Constructor method
	 */
	public Reader(String cacheServer) {
		mapper = new ObjectMapper();
		objectWriter = mapper.writer();
		initializeReader(cacheServer);
	}

	/**
	 * This method is used to return instance of hazelcast client
	 * 
	 * @return
	 */
	public HazelcastInstance getHazelCastClient() {
		return client;
	}

	/**
	 * This method is used to shutdown hazelcast client instance
	 */
	public void shutdown() {
		client.getLifecycleService().shutdown();
	}

	private static Properties readPropertyFile(String confFile) {

		logger.debug("readPropertyFile method :: Start ");

		Properties properties = new Properties();

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(confFile));
		} catch (FileNotFoundException e) {
			logger.error("Error reading configuration file. File " + confFile + " does not exist", e);
		}

		try {
			properties.load(inputStream);
			logger.debug("Configuration file : " + confFile + " loaded successfully " + properties);

			if (properties.size() == 0) {
				logger.error("Error reading configuration file. File " + confFile + " does not contain any property");
			}

		} catch (IOException e) {
			logger.error("Error reading configuration file : " + confFile, e);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Error closing input stream in readPropertyFile()", e);
				}
			}
		}

		logger.debug("readPropertyFile method :: End ");

		return properties;
	}

	public static Properties getCachingConfig() {
		Properties cachingProps = null;
		try {
			cachingProps = readPropertyFile("/usr/local/impetus_lib/resources/hazelcast-server.properties");
		} catch (Exception e) {
			logger.error("Error while reading configuration file", e);
		}
		return cachingProps;

	}

	/**
	 * This method is used to initialize reader instance
	 **/
	@SuppressWarnings("rawtypes")
	private void initializeReader(String cacheServer) {
		Properties cacheProps = null;
		try {
			cacheProps = getCachingConfig();
			// cacheProps =
			// "/usr/local/impetus_lib/resources/hazelcast-server.properties";
		} catch (Exception e) {
			logger.error("Error reading resources", e);
			throw new RuntimeException("Error reading resources ", e);
		}

		ClientConfig clientConfig = new ClientConfig();

		String strClients = null;
		strClients = cacheProps.getProperty(cacheServer);

		if (null == strClients) {
			logger.error("Unable to fetch cache server addresses from config file ");
			throw new RuntimeException("Could not connect to cache server server ip's null  ");
		}

		String[] addrSplits = strClients.split(COMMA_SEPARATOR);
		for (String addr : addrSplits) {
			clientConfig.getNetworkConfig().addAddress(addr);
		}

		long t1 = System.currentTimeMillis();

		client = HazelcastClient.newHazelcastClient(clientConfig);

		logger.info("Serializers set");

		logger.info("Established connection with cache server " + strClients);

		if (null == client) {
			logger.error("Could not connect to cache server " + strClients);
			throw new RuntimeException("Could not connect to cache server " + strClients);
		}
		logger.info("Time taken to initiate cache client " + (System.currentTimeMillis() - t1) + " msec");
	}

	/**
	 * This method is used to initialize CDMA NAS IP Cache Map
	 */
	public IMap<String, String> initializeTestMap() {
		try {
			testMap = client.getMap("testMap");
			logger.info("Loaded NasIpCacheMap");
		} catch (Exception e) {
			logger.error("Error while fetching testMap from cache server. ", e);
			testMap = (IMap<String, String>) Collections.EMPTY_MAP;
		}
		return testMap;
	}

	/**
	 * This method is used to get CDMA NAS IP Cache Map
	 */
	public IMap<String, String> getTestMap() {
		return testMap;
	}

}
