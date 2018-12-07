# Example
This document will explain how to load maps in hazelcast and view them on screen

## Steps to load sample data in Hazelcast cluster
Below steps can be followed to load sample data in Hazelcast IMDG cluster:
- Go to the directory where hazelcast cluster is installed and navigate to `bin` directory.
- Add the map's information in `hazelcast.xml`.For a sample on how to add map information refer `hazelcast.xml` present under `hazelcast-cluster/src/main/resources`.
- Write loader class to load data in Hazelcast map. For sample refer `HazelcastMapLoader.java` present under `hazelcast-cluster/src/main/java/com/impetus/hazelcast/example`.
- Run `HazelcastMapLoader.java` class to ingest records in `testMap` using below commands:
   - Add `hazelcast-cluster-startup-1.0.0.jar` and lib present in `<path to jar>/hazelcast-cluster-startup-1.0.0` folder to classpath: `CLASSPATH="<path to jar>/hazelcast-cluster-startup-1.0.0.jar:<path to jar>/lib/*`"
   - Add main class: `CLASSNAME=com.impetus.hazelcast.example.HazelcastMapLoader`
   - Call main class: `java -classpath $CLASSPATH $CLASSNAME`
   - This should load sample map in hazelcast cluster. 
   - You will see following logs as shown below:
   
   
		`Apr 20, 2018 3:04:29 PM com.hazelcast.core.LifecycleService
		INFO: HazelcastClient[hz.client_0_dev][3.4.6] is STARTING
		Apr 20, 2018 3:04:29 PM com.hazelcast.core.LifecycleService
		INFO: HazelcastClient[hz.client_0_dev][3.4.6] is STARTED
		Apr 20, 2018 3:04:29 PM com.hazelcast.core.LifecycleService
		INFO: HazelcastClient[hz.client_0_dev][3.4.6] is CLIENT_CONNECTED
		Apr 20, 2018 3:04:29 PM com.hazelcast.client.spi.impl.ClusterListenerThread
		INFO: 
		Members [1] {
		Member [localhost]:5701
		}`
## Steps to check value in map
- Open browser and enter url `http:<IP Address>:9000/#/hazelcast`
- To see the data in map, Go to `Maps` and then to `testMap`.
- Click on `Browse`.
- Add the key in it and click on `Browse` button. In `testMap` the keys are : `Key1,Key2...Key999`.
- To clear the Map record from UI, click on `Clear` tab. It will not remove the data from Hazelcast, but will only clear the screen.
