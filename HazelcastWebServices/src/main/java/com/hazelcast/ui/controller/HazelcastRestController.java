/**
 * Name           : HazelcastRestController.java
 * Type           : JAVA
 * Purpose        : Entry method for Hazelcast webservices
 * Description    : 
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------  
**/

package com.hazelcast.ui.controller;

import com.hazelcast.ui.service.HazelcastRestService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller.
 * @author Sourav Gulati
 * @Description Controller class for webservices related to hazelcast library
 *
 */
@Component
@RestController
public class HazelcastRestController {

  private static final Logger logger = Logger.getLogger(HazelcastRestController.class);
  
  @Autowired
  private HazelcastRestService hazelcastRestService;

  /**
   * welcome method which gets involed whenver base url is looked up.
   * @return
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String welcome() {
    logger.info("Hazelcast Web UI base method called");
    return "Welcome to Hazelcast Web UI";
  }

  /**
   * Returns members in the cluster.
   * @return
   */
  @RequestMapping(value = "/clusterinfo", method = RequestMethod.GET)
  public String getClusterInfo() {
    return hazelcastRestService.getMembersInfo();
  }

  /**
   * Returns list of maps present in the cluster.
   * @return
   */
  @RequestMapping(value = "/mapsName", method = RequestMethod.GET)
  public String getMapsName() {
    return hazelcastRestService.getMapsName();
  }

  /**
   * Returns size of map.
   * @param mapName input map
   * @return
   */
  @RequestMapping(value = "/getSize/{mapName}", method = RequestMethod.GET)
  public String getSize(@PathVariable("mapName") String mapName) {
    return hazelcastRestService.getSize(mapName);
  }

  /**
   * Returns value corresponding to input key.
   * @param mapName input map
   * @param key input key
   * @param type input type
   * @return
   */
  @RequestMapping(value = "/getValue/{mapName}/{key}/{type}", method = RequestMethod.GET)
  public String getValue(@PathVariable("mapName") String mapName, 
      @PathVariable("key") String key, @PathVariable("type") String type) {
    return hazelcastRestService.getValueFromMap(mapName, key, type);
  }
  
}
