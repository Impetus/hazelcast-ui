package com.hazelcast.ui.controller;

import com.hazelcast.ui.service.JMXService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * JMXService class.
 * @author Sourav Gulati
 * @Description Controller class for webservices related to JMX library
 */
@Component
@RestController
public class JmxController {

	@Autowired
	private JMXService jmxService;
	/**
	 * This method fetches information of node memory
	 * members of the hazelcast cluster.
	 * @param host .
	 * @param port .
	 * @return the node memory
	 */
	@RequestMapping(value = "/nodememory/{host}/{port}",
	method = RequestMethod.GET)
	public String getMemoryInfo(@PathVariable("host") final String host,
			@PathVariable("port") final String port) {
		return jmxService.getNodeMemoryInfo(host, port);
	}

	/**
	 * This method fetches information of maps loaded in hazelcast cluster.
	 * @return memory stats
	 * @param host .
	 * @param port .
	 * @param mapName .
	 */
	@RequestMapping(value = "/mapmemory/{host}/{port}/{mapName}",
	method = RequestMethod.GET)
	public String getMapMemoryStats(@PathVariable("host") final String host,
			@PathVariable("port") final String port,
			@PathVariable("mapName") final String mapName) {
		return jmxService.getMapMemoryStats(host, port, mapName);
	}

}
