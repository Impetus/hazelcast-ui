package com.hazelcast.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

@Component
public class CacheInstance {

	@Value("${etl.cluster.nodes}")
	private String etlCluster;

	private HazelcastInstance client = null;
	private String COMMA_SEPARATOR = ",";

	public HazelcastInstance getClient() {
		return client;
	}

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