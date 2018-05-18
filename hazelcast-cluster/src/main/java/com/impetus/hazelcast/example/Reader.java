/**
 * Name           : Reader.java
 * Type           : JAVA
 * Purpose        : This class loads hazelcast property file
 * Description    : 
 * Mod Log
 * Date		    By		         	Jira			Description
 * ----------- 	----------------- 	---------- 		---------------	
**/
package com.impetus.hazelcast.example;

import java.io.File;
import java.io.FileInputStream;
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

@SuppressWarnings("unchecked")
public class Reader {
	private static final String COMMA_SEPARATOR = ",";
	private static final Logger logger = LogUtils.getLogger(Reader.class);
	
	ObjectMapper mapper = null;
	ObjectWriter objectWriter = null;
	HazelcastInstance client = null;
	IMap<String, String> testMap = null;
	IList<String> testList = null;
	
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

	/**
	 * This method is used to load property file
	 * @param confFile
	 * @return
	 */
	public static Properties readPropertyFile(String confFile) {

		logger.debug("readPropertyFile method :: Start ");

		Properties properties = new Properties();
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(new File(confFile));
			properties.load(inputStream);

			if (properties.size() == 0) {
				logger.error("Error reading configuration file. File {} does not contain any property",confFile);
			}
			
		} catch (FileNotFoundException e) {
			logger.error("Error reading configuration file. File " + confFile + " does not exist", e);
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

	/**
	 * Loads hazelcast server properties from config file
	 * @return
	 */
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
	 * This method is used to initialize reader instance which inturn initializes hazelcast client
	 **/
	@SuppressWarnings("rawtypes")
	public boolean initializeReader(String cacheServer) {
		
		Properties cacheProps = null;
		ClientConfig clientConfig = new ClientConfig();
		String strClients = null;
		boolean isStarted=false;

		try {
			cacheProps = getCachingConfig();
		} catch (Exception e) {
			logger.error("Error reading resources", e);
			throw new RuntimeException("Error reading resources ", e);
		}

		if (cacheProps !=null ) {
			logger.info("Loaded hazelcast properties from property file");

			// Fetch list of hazel cast nodes configured using key cache.server in hazelcast-server.properties
			strClients = cacheProps.getProperty(cacheServer);
		
			if (null == strClients) {
				logger.error("Unable to fetch cache server addresses from config file ");
				throw new RuntimeException("Could not connect to cache server server..No IPs configured");
			}
			
			logger.info("Established connection with cache server {}", strClients);
			
			String[] addrSplits = strClients.split(COMMA_SEPARATOR);
			for (String addr : addrSplits) {
				clientConfig.getNetworkConfig().addAddress(addr);
			}
		
			long t1 = System.currentTimeMillis();
		
			// Instantiating hazelcast client
			client = HazelcastClient.newHazelcastClient(clientConfig);
			logger.info("Serializers set");
			if (null == client) {
				logger.error("Could not instantiate client");
				throw new RuntimeException("Could not instantiate client");
			}
			isStarted=true;
			logger.info("Hazelcast client instantiated successfully. Time taken to initiate cache client is {} msec",(System.currentTimeMillis() - t1));
		} else {
			logger.error("Unable to fetch cache server addresses from config file ");
			throw new RuntimeException("Could not connect to cache server server..Not able to load property file");
		}
		return isStarted;
	}

	/**
	 * This method is used to initialize test Map
	 */
	public IMap<String, String> initializeTestMap() {
		
		try {
			testMap = client.getMap("testMap");
			logger.info("Loaded sample map. Size of map is {}",testMap.size());
		} catch (Exception e) {
			logger.error("Error while fetching testMap from cache server. ", e);
			testMap = (IMap<String, String>) Collections.EMPTY_MAP;
		}
		return testMap;
	}

	
	/**
	 * This method is used to initialize test List
	 */
	public IList<String> initializeTestList() {
		
		try {
			testList = client.getList("testList");
			
			logger.info("Loaded sample list. Size of list is {}",testList.size());
		} catch (Exception e) {
			logger.error("Error while fetching testList from cache server. ", e);
			testList = (IList<String>) Collections.EMPTY_LIST;
		}
		return testList;
	}
	
	/**
	 * This method is used to get test Map
	 */
	public IMap<String, String> getTestMap() {
		return testMap;
	}
	
	/**
	 * This method is used to get test list
	 */
	public IList<String> getTestList() {
		return testList;
	}

}
