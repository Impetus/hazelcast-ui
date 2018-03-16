package com.hazelcast.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.management.remote.JMXConnector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.StreamSerializer;
/**
 * 
 * @author Sourav Gulati
 * @Description Class to instantiate hazelcast client
 */
@Component
public class CacheInstance {

	@Value("${etl.cluster.nodes}")
	private String etlCluster;

	private HazelcastInstance client = null;
	private static final String COMMA_SEPARATOR = ",";
	private static final Map<String, JMXConnector> JMX_CONNECTION_CACHE = new ConcurrentHashMap<>();

	/**
	 * @Description This method is used return the instance of hazelcast client
	 * @return HazelcastInstance
	 */
	public HazelcastInstance getClient() {
		return client;
	}
    
	/**
	 * @Description This method is used instantiate hazelcast instance 
	 */
	@SuppressWarnings("rawtypes")
	@PostConstruct
	private void initializeInstance() {
		ClientConfig clientConfig = new ClientConfig();
		String[] addrSplits = etlCluster.split(COMMA_SEPARATOR);
		for (String addr : addrSplits) {
			clientConfig.getNetworkConfig().addAddress(addr);
		}

		// Add all the serializers
		Map<Class, StreamSerializer> configMap = getSerializerConfigs();
		for (Map.Entry<Class, StreamSerializer> entry : configMap.entrySet()) {
			SerializerConfig serializer = new SerializerConfig().setTypeClass(entry.getKey())
					.setImplementation(entry.getValue());
			clientConfig.getSerializationConfig().addSerializerConfig(serializer);
		}
		client = HazelcastClient.newHazelcastClient(clientConfig);
	}

	@SuppressWarnings("rawtypes")
	private Map<Class, StreamSerializer> getSerializerConfigs() {
		Map<Class, StreamSerializer> configMap = new HashMap<Class, StreamSerializer>();

		configMap.put(CurrentAlertThresholdInfo.class, new CurrentThresholdInfoSerializer());
		configMap.put(DailyUsage.class, new DailyUsageMapSerializer());
		configMap.put(DeviceDefaultCache.class, new DeviceDefaultCacheSerializer());

		return configMap;
	}

	public JMXConnector getConnectionForHost(final String ipAddress) {
		return JMX_CONNECTION_CACHE.get(ipAddress);
	}

	public void setConnectionForHost(final String ipAddress, final JMXConnector connection) {
		JMX_CONNECTION_CACHE.put(ipAddress, connection);
	}
}