# Starting Hazelcast IMDG cluster
- Download Hazelcast IMDG 3.4.6 from [here](https://hazelcast.org/download/).
- Go to `Hazelcast Dir`/bin.
- To use the Custom UI one must start Hazelcast with JMX enabled. 
  - Open `server.sh` and replace `$RUN_JAVA -server $JAVA_OPTS com.hazelcast.core.server.StartServer` with 
  `$RUN_JAVA -server $JAVA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dhazelcast.jmx=true com.hazelcast.core.server.StartServer`. 
- Open `hazelcast.xml` and disable `multicast enabled`. i.e. replace `<multicast enabled="true">` with `<multicast enabled="false">`.
- In above file enable `tcp-ip` and add the `IP/hostnames of hazelcast cluster members` - 
   - set `<tcp-ip enabled="true">`
   - add cluster members -  `<member>Node IP</member>`
- Open Terminal, navigate to `Hazelcast Dir`/bin and start cluster by running command 
  `sudo -bE bin/server.sh`.

# Starting Inbuilt Hazelcast cluster 
- Checkout hazelcast-cluster code from ![here](https://github.com/Impetus/hazelcast-ui/tree/master/hazelcast-cluster).
- Go to resources folder
  `cd hazelcast-cluster/src/main/resources`
- Add IPs of the nodes on which hazelcast instance need to be run. This will be added against key `cache.server` in `hazelcast-server.properties` file. In case of multiple nodes the value will be "," separated. E.g: `Node-1 ip:5701`,`Node-2 IP:5701`. For testing purpose one can use localhost.
- Add IPs of the nodes on which hazelcast instance need to be run in file `hazelcast.xml` as well.In case of multiple nodes add a new `member` tag per IP. For testing purpose one can use localhost.
- By default the logs of hazelcast will be created al location : `/mnt/hazelcast_logs/`. One can update it in file `hazelcast-server-log4j.properties`.
- Got to base folder of hazelcast cluster : `cd hazelcast-cluster`
- Build the code using: `mvn clean install`
- In target folder zipped package will get created with name `hazelcast-cluster-startup-1.0.0-pkg.tar.gz`. 
- Extract the tar using command `tar -xvzf hazelcast-cluster-startup-1.0.0-pkg.tar.gz` to the wished location.
- Once the tar is extracted it will create the dir `hazelcast-cluster-startup-1.0.0`. This dir will have `conf,bin,lib folders and hazelcast-cluster-startup-1.0.0.jar`.
- Add the `<path to conf>/hazelcast-cluster-startup-1.0.0/conf/*` into the **CLASSPATH**.
- Spawn hazelcast instance using command: `sudo -bE <path to jar>/hazelcast-cluster-startup-1.0.0/bin/start-hazelcast.sh <Java Xmx> <Java MaxHeapFreeRatio> <Java MinHeapFreeRatio>`.

**NOTE**: Copy the extracted `hazelcast-cluster-startup-1.0.0` to all the nodes of Hazelcast cluster and execute #11-12 on each node of hazelcast cluster.
