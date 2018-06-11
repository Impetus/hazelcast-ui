/**
 * Name           : HazelcastRestService.java
 * Type           : JAVA
 * Purpose        : This class is the service layer containing
 *                  implementation of methods exposed by the webservice
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.hazelcast.ui.service;

import com.eclipsesource.json.ParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.Member;
import com.hazelcast.util.CacheInstance;

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

/**
 * HazelcastRestService implementation.
 * @author Sourav Gulati
 * @Description Service class for webservices related to hazelcast library
 */
@Component
public class HazelcastRestService {

	@Autowired
	private CacheInstance cacheInstance;

	private ObjectMapper objectMapper;
	private SimpleDateFormat sdf;

	/**
	 * Method initializes ObjectMapper and SimpleDateFormat.
	 *              cluster.
	 */
	@PostConstruct
	private void intialize() {
		objectMapper = new ObjectMapper();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	}

	/**
	 * Method to return members info.
	 * @Description This method is used to retrieve list of members of hazelcast
	 *              cluster.
	 * @return json list of hazelcast members
	 */
	public String getMembersInfo() {
		final List<String> members = new ArrayList<>();
		for (Member m : cacheInstance.getClient().getCluster().getMembers()) {
			members.add(m.getSocketAddress().getHostString() + ":"
					+ m.getSocketAddress().getPort());
		}

		try {
			return objectMapper.writeValueAsString(members);
		} catch (final JsonProcessingException e) {
			return (e.getMessage());
		}
	}

	/**
	 * Method to return maps info.
	 * @Description This method is used to retrieve list of maps of hazelcast
	 *              cluster.
	 * @return json list of hazelcast maps
	 */
	public String getMapsName() {
		final List<String> maps = new ArrayList<>();
		final Collection<DistributedObject> distributedObjects =
				cacheInstance.getClient().getDistributedObjects();
		for (DistributedObject distributedObject : distributedObjects) {
			if (distributedObject.getServiceName()
					.equals("hz:impl:mapService")) {
				maps.add(distributedObject.getName());
			}
		}
		try {
			return objectMapper.writeValueAsString(maps);
		} catch (final JsonProcessingException e) {
			return (e.getMessage());
		}
	}

	/**
	 * Returns size of map.
	 * @param mapName
	 *            input map
	 * @return Size of the map .
	 */
	public String getSize(final String mapName) {
		final int size = cacheInstance.getClient().getMap(mapName).size();
		final Map<String, Integer> sizeMap = new HashMap<>();
		sizeMap.put("Size", size);
		try {
			return (objectMapper.writeValueAsString(sizeMap));
		} catch (final JsonProcessingException e) {
			return ("Exception occurred while fecthing entry" + e);
		}

	}

	/**
	 * Method to return value from map.
	 * @Description This method is used to retrieve value corresponding to given
	 *              in hazelcast map.
	 * @param mapName
	 *            input map
	 * @param key
	 *            input key
	 * @param type
	 *            of key input type
	 * @return json string of value retrieved from map
	 */
	public String getValueFromMap(final String mapName,
			final String key, final String type) {
		Object mapKey = null;
		final Map<String, Object> entryMap = new HashMap<>();
		try {
			mapKey = getKey(type, key);
		} catch (final ParseException e) {
			return "{\"Value\":\"No Value Found\"}";
		}
		if (mapKey == null || cacheInstance.getClient().getMap(mapName)
				.get(mapKey) == null) {
			return "{\"Value\":\"No Value Found\"}";
		}

		final EntryView<Object, Object> entry =
				cacheInstance.getClient().getMap(mapName).getEntryView(mapKey);

		final Calendar cal = Calendar.getInstance();
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
		} catch (final JsonProcessingException e) {
			return ("Exception occurred while fecthing entry" + e);
		}
	}

	/**
	 * Method to get key details.
	 * @Description This method is used to retrieve object initialized
	 *              corresponding to data type of key.
	 * @param type
	 *            input key type
	 * @param key
	 *            input key
	 * @return Object initialized corresponding to data type of key
	 * @throws ParseException
	 *             exception
	 */
	public Object getKey(final String type,
			final String key) throws ParseException {
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
		default:
		}
		return outKey;
	}
}
