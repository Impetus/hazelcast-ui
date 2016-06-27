package com.hazelcast.ui.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eclipsesource.json.ParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.Member;
import com.hazelcast.util.CacheInstance;

@Component
public class HazelcastRestService {

	@Autowired
	private CacheInstance cacheInstance;

	private ObjectMapper objectMapper;
	private SimpleDateFormat sdf;

	@PostConstruct
	private void intialize() {
		objectMapper = new ObjectMapper();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	}

	public String getMembersInfo() {
		List<String> members = new ArrayList<>();

		for (Member m : cacheInstance.getClient().getCluster().getMembers()) {
			members.add(m.getSocketAddress().getHostString() + ":" + m.getSocketAddress().getPort());

		}

		try {
			return objectMapper.writeValueAsString(members);
		} catch (JsonProcessingException e) {
			return (e.getMessage());
		}
	}

	public String getMapsName() {
		List<String> maps = new ArrayList<>();
		Collection<DistributedObject> distributedObjects = cacheInstance.getClient().getDistributedObjects();
		for (DistributedObject distributedObject : distributedObjects) {
			if (distributedObject.getServiceName().equals("hz:impl:mapService")) {
				maps.add(distributedObject.getName());
			}
		}
		try {
			return objectMapper.writeValueAsString(maps);
		} catch (JsonProcessingException e) {
			return (e.getMessage());
		}
	}

	public String getSize(String mapName) {
		int size = cacheInstance.getClient().getMap(mapName).size();
		Map<String, Integer> sizeMap = new HashMap<>();
		sizeMap.put("Size",size);
		try {
			return (objectMapper.writeValueAsString(sizeMap));
		} catch (JsonProcessingException e) {
			return ("Exception occurred while fecthing entry" + e);
		}
		
	}

	public String getValueFromMap(String mapName, String key, String type) {
		Calendar cal = Calendar.getInstance();
		Object mapKey = null;
		Map<String, Object> entryMap = new HashMap<>();
		try {
			mapKey = getKey(type, key);
		} catch (ParseException e) {
			return "{\"Value\":\"No Value Found\"}";
		}
		if (mapKey == null || cacheInstance.getClient().getMap(mapName).get(mapKey) == null) {
			 return "{\"Value\":\"No Value Found\"}";
		}

		EntryView<Object, Object> entry = cacheInstance.getClient().getMap(mapName).getEntryView(mapKey);
		

		entryMap.put("Value", entry.getValue());
		cal.setTimeInMillis(entry.getCreationTime());
		entryMap.put("Creation_Time", sdf.format(cal.getTime()));
		cal.setTimeInMillis(entry.getLastUpdateTime());
		entryMap.put("Last_Update_Time", sdf.format(cal.getTime()));
		cal.setTimeInMillis(entry.getLastAccessTime());
		entryMap.put("Last_Access_Time", sdf.format(cal.getTime()));
		cal.setTimeInMillis(entry.getExpirationTime());
		entryMap.put("Expiration_Time", sdf.format(cal.getTime()));

		try {
			return (objectMapper.writeValueAsString(entryMap));
		} catch (JsonProcessingException e) {
			return ("Exception occurred while fecthing entry" + e);
		}
	}

	public Object getKey(String type, String key) throws ParseException {
		Object outKey = null;
		switch (type) {
		case "String":
			outKey = key;
			break;
		case "Integer":
			outKey = Integer.parseInt(key);
			break;
		case "Long":
			outKey = Long.parseLong(key);
			break;
		}
		return outKey;
	}
}
