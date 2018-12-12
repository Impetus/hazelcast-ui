[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#/builds/Impetus/hazelcast-ui)
[![](https://travis-ci.org/Impetus/hazelcast-ui.svg?branch=master)](https://travis-ci.org/Impetus/hazelcast-ui) 

# Hazelcast-ui

Hazelcast is an in memroy database which has its native user interface with multiple functionalities. This native (development) version of Hazelcast is restricted to two nodes only. Our application provides all basic capabilites of Hazelcast user intarface with no restriction on number of nodes.

The app has been tested with Hazelcast 3.4.6 version.

## Prerequisites
- JDK 7
- Apache Tomcat 7.0.40 or later
- All Hazelcast instances should be JMX enabled(By default port used for jmx is 1010, it can be changed as per requirement)
- grunt-cli 1.2.0 or later
- Nodejs
- Maven
- Ubuntu 14.04

## Documentation

* ![How it works](how_it_works.md)
* ![Features](features.md)
* ![Starting Hazelcast Cluster](starting_hazelcast.md)
* ![Building and Deploying](building_deploying.md)
* ![Example](example.md)
* ![Troubleshooting](troubleshooting.md)

## Future Enhancements

* Distributed cache implementation for List (https://github.com/Impetus/hazelcast-ui/issues/6).
* Distributed cache implementation for Set.
* Distributed cache implementation for Queues.
* Upgrade Hazelcast to 3.11 (https://github.com/Impetus/hazelcast-ui/issues/5).
