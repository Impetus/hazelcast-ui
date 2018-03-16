package com.hazelcast.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.ui.service.HazelcastRestService;
/**
 * 
 * @author Sourav Gulati
 * @Description Controller class for webservices related to hazelcast library
 *
 */
@Component
@RestController
public class HazelcastRestController {

	@Autowired
	private HazelcastRestService hazelcastRestService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "Welcome to Hazelcast Web UI";
	}

	@RequestMapping(value = "/clusterinfo", method = RequestMethod.GET)
	public String getClusterInfo() {
		return hazelcastRestService.getMembersInfo();
	}

	@RequestMapping(value = "/mapsName", method = RequestMethod.GET)
	public String getMapsName() {
		return hazelcastRestService.getMapsName();
	}

	@RequestMapping(value = "/getSize/{mapName}", method = RequestMethod.GET)
	public String getSize(@PathVariable("mapName") String mapName) {
		return hazelcastRestService.getSize(mapName);
	}

	@RequestMapping(value = "/getValue/{mapName}/{key}/{type}", method = RequestMethod.GET)
	public String getValue(@PathVariable("mapName") String mapName, @PathVariable("key") String key, @PathVariable("type") String type) {
		return hazelcastRestService.getValueFromMap(mapName, key, type);
	}
}
