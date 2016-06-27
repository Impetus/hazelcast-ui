package com.hazelcast.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.ui.service.JMXService;

@Component
@RestController
public class JmxController {

	@Autowired
	private JMXService jmxService;
	
	@RequestMapping(value="/nodememory/{host}/{port}",method=RequestMethod.GET)
	public String getMemoryInfo(@PathVariable("host") String host, @PathVariable("port") String port)
	{
		return jmxService.getNodeMemoryInfo(host, port);
	}
	
	@RequestMapping(value="/mapmemory/{host}/{port}/{mapName}",method=RequestMethod.GET)
	public String getMapMemoryStats(@PathVariable("host") String host, @PathVariable("port") String port,@PathVariable("mapName")String mapName)
	{
		return jmxService.getMapMemoryStats(host, port,mapName);
	}
	
}
