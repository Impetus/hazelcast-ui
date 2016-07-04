package com.hazelcast.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
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
	private String COMMA_SEPARATOR = ",";

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
	@PostConstruct
	private void initializeInstance() {
		ClientConfig clientConfig = new ClientConfig();
		String[] addrSplits = etlCluster.split(COMMA_SEPARATOR);
		for (String addr : addrSplits) {
			clientConfig.getNetworkConfig().addAddress(addr);
		}

		client = HazelcastClient.newHazelcastClient(clientConfig);
	}


}