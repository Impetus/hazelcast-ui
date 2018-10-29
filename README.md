The development version of Hazelcast is restricted to two nodes only. This application provides basic capabilites of Hazelcast UI with no restriction on number of nodes.
The app has been tested with Hazelcast 3.4.6 version.

## Table of Contents
- Pre-requisites for Hazelcast UI APP 
- The technical Stack
- Building and Deploying Hazelcast Webservices
- Building and starting Hazelcast UI APP
- Starting Hazelcast IMDG cluster
- Starting Inbuilt Hazelcast cluster
- Steps to load sample data in Hazelcast cluster
- Troubleshooting steps
- Features
- Further Improvements

## Prerequisites
- Java 7
- Apache Tomcat 7.0.40 or later
- All Hazelcast instances should be JMX enabled(By default port used for jmx is 1010, it can be changed as per requirement)
- grunt-cli 1.2.0 or later
- Nodejs
- Maven
- Ubuntu 14.04

## Technical Stack
![ts](/images/Tech_stack.png)
***

## Building and Deploying Hazelcast Webservices
Pre-requisite to starting Hazelcast Webservices component is that the Hazelcast cluster must be up and running. 
In case you do not have an existing hazelcast cluster, [you can download it from hazelcast.org](http://git-impetus/bigdata/HazelcastUIApp#building-and-deploying-hazelcast-webservices) or [use the hazelcast cluster that comes bundled with this product](http://git-impetus/bigdata/HazelcastUIApp#starting-inbuilt-hazelcast-cluster-).

Hazelcast Webservices work by connecting to a hazelcast cluster and provide a wrapper layer to access hazelcast api's. Follow below steps to build and deploy Hazelcast Webservices.
- Checkout the code for Git Repository.
- Go to the webservices folder - cd $GIT_CLONE_DIR/HazelcastWebServices.
- Update the "etl.cluster.nodes" property in src/main/resources/app-config.properties with list of hazelcast instances (you can create comma separated list of instances if there are more than one instance).Make sure to prepend the env in "app-config.properties" e.g for local the filename should be "local-app-config.properties". Similarly for other envs.
- Multiple conf file can be added parallel to "`<env>`-app-config.properties" per env e.g "prod-app-config.properties" for production env "preprod-app-config.properties" for preprod and "local-app-config.properties" for local env etc.
- Build the code using command "mvn clean install".
- Go to target directory and copy HazelcastWebServices.war to webapps dir of Apache Tomcat.
- Once tomcat server is up and running, Hazelcast webservices are also up. Open browser and go to url - http:`<IP Address>`:`<tomcat port>`/HazelcastWebServices/
7. You will see a message "Welcome to Hazelcast Web UI" indicating that webservices have got started successfully.

## Building and starting Hazelcast UI APP
- Go to UI directory  - cd $GIT_CLONE_DIR/UI_Code
- Check for presence of "node_modules" directory. If present delete it otherwise go to next step.
- Go to app/scripts directory and edit app.js
   - Change localhost to `IP of the node`.
- Go to Apache Tomcat installation directory. Navigate to bin directory and create a file setenv.sh (if not already present).
- setenv.sh is used to set the environment where the code is being deployed. Add below line at the end of file:
   - JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF8 -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -DDEPLOY_ENV=`env`". Here `env` could be local/prod/preprod as per the prefix of filename "HazelcastWebServices/src/main/resources/local-app-config.properties". 
-Start tomcat server.
- Open terminal, log in as "root" user and navigate to "$GIT_CLONE_DIR/UI_Code" directory. Run below commands: 
   - npm install
   - npm install karma --save-dev
   - npm install karma-jasmine karma-chrome-launcher jasmine-core --save-dev
   - npm install -g grunt-cli
   - nohup grunt --force serve &
   - If you get an error "Cannot find module : sigmund" then run npm install sigmund followed by Step 5 again.
- Once above steps have been run successfully Hazelcast UI is up and running. It can be accessed at url - http:`<IP Address>`:9000/#/hazelcast

# Starting Hazelcast IMDG cluster
- Download Hazelcast IMDG 3.4.6 from hazelcast.org.
- Go to `Hazelcast Dir`/bin.
- To use the Custom UI one must start Hazelcast with JMX enabled. To do this open "server.sh" and replace "$RUN_JAVA -server $JAVA_OPTS com.hazelcast.core.server.StartServer" with "$RUN_JAVA -server $JAVA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dhazelcast.jmx=true com.hazelcast.core.server.StartServer". 
- Open "hazelcast.xml" and disable "multicast enabled". i.e. replace `<multicast enabled="true">` with `<multicast enabled="false">`.
- In above file enable tcp-ip and add the IP/hostnames of hazelcast cluster members - 
   - set `<tcp-ip enabled="true">`
   - add cluster members -  `<member>Node IP</member>`
- Open Terminal, navigate to `Hazelcast Dir`/bin and start cluster by running command `sudo -bE bin/server.sh`.

## Starting Inbuilt Hazelcast cluster 
- Checkout "hazelcast-cluster" code from Git repository.
- Go to resources folder : cd hazelcast-cluster/src/main/resources
- Add IPs of the nodes on which hazelcast instance need to be run. This will be added against key cache.server in hazelcast-server.properties file. In case of multiple nodes the value will be "," separated. E.g: `Node-1 ip`:5701,`Node-2 IP`:5701. For testing purpose one can use localhost.
- Add IPs of the nodes on which hazelcast instance need to be run in file "hazelcast.xml" as well.In case of multiple nodes add a new `member` tag per IP. For testing purpose one can use localhost.
- By default the logs of hazelcast will be created al location : /mnt/hazelcast_logs/. One can update it in file hazelcast-server-log4j.properties.
- Got to base folder of hazelcast cluster : cd hazelcast-cluster
- Build the code using: mvn clean install
- In target folder zipped package will get created with name "hazelcast-cluster-startup-1.0.0-pkg.tar.gz". 
- Extract the tar using command `tar -xvzf hazelcast-cluster-startup-1.0.0-pkg.tar.gz` to the wished location.
- Once the tar is extracted it will create the dir `hazelcast-cluster-startup-1.0.0`. This dir will have `conf,bin,lib folders and hazelcast-cluster-startup-1.0.0.jar`.
- Add the <path to conf>/hazelcast-cluster-startup-1.0.0/conf/* into the CLASSPATH.
- Spawn hazelcast instance using command: sudo -bE <path to jar>/hazelcast-cluster-startup-1.0.0/bin/start-hazelcast.sh <Java Xmx> <Java MaxHeapFreeRatio> <Java MinHeapFreeRatio>.

NOTE:Copy the extracted `hazelcast-cluster-startup-1.0.0` to all the nodes of Hazelcast cluster and execute #11-12 on each node of hazelcast cluster.

## Steps to load sample data in Hazelcast cluster
Below steps can be followed to load sample data in Hazelcast IMDG cluster as well as Inbuilt Hazelcast cluster.
Go to the directory where hazelcast cluster is installed and navigate to bin directory.
- Add the map's information in "hazelcast.xml".For a sample on how to add map information refer hazelcast.xml present under hazelcast-cluster/src/main/resources.
- Write loader class to load data in Hazelcast map. For sample refer HazelcastMapLoader.java present under hazelcast-cluster/src/main/java/com/impetus/hazelcast/example.
- Run HazelcastMapLoader.java class to ingest records in "testMap" using below commands:
   - Add hazelcast-cluster-startup-1.0.0.jar and lib present in `<path to jar>`hazelcast-cluster-startup-1.0.0 folder to classpath: CLASSPATH="`<path to jar>`/hazelcast-cluster-startup-1.0.0.jar:`<path to jar>`/lib/*"
   - Add main class: CLASSNAME=com.impetus.hazelcast.example.HazelcastMapLoader
   - Call main class: java -classpath $CLASSPATH $CLASSNAME
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

#  Troubleshooting Steps
Follow below steps if in any case the Hazelcast Custom UI does not show cluster(memory utilization) or member information.

- Check if Hazelcast cluster is up and running. If yes then simply restart Tomcat service.
- If Hazelcast cluster is down then first start hazelcast cluster followed by restart of Tomcat service
- If "npm install" gives certificate issue then execute command "npm config set strict-ssl false" from $GIT_CLONE_DIR/UI_Code" directory.
- If unable to find Node during the command ‘npm install’. The issue is due to the name difference. As in system it is nodejs while code is looking for node. To resolve the issue run command "sudo ln -s /usr/bin/nodejs /usr/bin/node". It will lync nodejs with node.
- If one faces permission issue due to the lock on folder in UI code. The execute command "sudo chmod 777 <path> -R" to resolve the issue
 



## Features

### Home Screen
![home](/images/Home_Screen.png)
***

### List of Members
- To see the data in map, Go to "Maps" and then to "testMap".Click on "Browse".
- Add the key in it and click on "Browse" button. In "testMap" the keys are : Key1,Key2...Key999.
- To clear the Map record from UI, click on "Clear" tab. It will not remove the data from Hazelcast, but will only clear the screen.
![members](/images/List_Members.png)
***

### List of Maps
![maps](/images/List_Maps.png)
***

### Map View
![mapview](/images/Map_Screen.png)
***

### Map Browser View
![mapbrowser](/images/Map_Browser.png)
***

## Further Improvements
- [ ] 
