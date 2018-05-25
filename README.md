Table of Contents
=================
[ About Hazelcast UI APP ] (http://git-impetus.impetus.co.in/bigdata/HazelcastUIApp#about-hazelcast-ui-app)


About Hazelcast UI APP
=============

Hazelcast UI provided in development version is restricted to two nodes.This APP provides basic capabilites of Hazelcast UI with no restriction on number of nodes.
This app is tested with Hazelcast 3.4.6 version.
In following steps we have provided 
1. Pre-requisites for Hazelcast UI APP. 
2. The technical Stack
3. How to Start Hazelcast cluster version: 3.4.6
4. How to Load data in Hazelcast.
5. Deployment steps of "Hazelcast UI Application" 
6. Features
7. Improvements

Prerequisites
-------------

1. Java 7 or later
2. Apache Tomcat 7.0.40 or later
3. All Hazelcast instances should be JMX enabled(By default port used for jmx is 1010, it can be changed as per requirement)
4. grunt-cli 1.2.0 or later
5. Nodejs

Tech stack
-------------
![ts](/images/Tech_stack.png)
***

Spawn Hazelcast cluster
------------------------
1. Checkout "hazelcast-cluster" code.
2. Go to resources folder : cd hazelcast-cluster/src/main/resources
3. Add IPs of the nodes on which hazelcast instance need to be run. This will be added against key cache.server in hazelcast-server.properties file. In case of multiple nodes the value will be "," separated. E.g: `Node-1 ip`:5701,`Node-2 IP`:5701. For testing purpose one can use localhost.
4. Add IPs of the nodes on which hazelcast instance need to be run in file "hazelcast.xml" as well.In case of multiple nodes add a new `member` tag per IP. For testing purpose one can use localhost.
5. By default the logs of hazelcast will be created al location : /mnt/hazelcast_logs/. One can update it in file hazelcast-server-log4j.properties.
6. Got to base folder of hazelcast cluster : cd hazelcast-cluster
7. Build the code using: mvn clean install -DskipTests
8. In target folder zipped package will get created with name "hazelcast-cluster-startup-1.0.0-pkg.tar.gz". 
9. Extract the tar using command `tar -xvzf hazelcast-cluster-startup-1.0.0-pkg.tar.gz` to the wished location.
10. Once the tar is extracted it will create the dir `hazelcast-cluster-startup-1.0.0`. This dir will have `conf,bin,lib folders and hazelcast-cluster-startup-1.0.0.jar`.
11. 
8. Create directories needed by the cluster: sudo mkdir -p /mnt/hazelcast_logs/
9. Copy the jar to relevent folder using commands :sudo cp target/hazelcast-1.0.0-jar-with-dependencies.jar /usr/local/impetus_lib
10. Go to resources folder and copy resource files to relevant location: 
   - cd hazelcast-cluster/src/main/resources
   - sudo cp * /usr/local/impetus_lib/resources
11. Go to bin folder and copy resource scripts to relevant location: 
   - cd hazelcast-cluster/src/main/bin 
   - sudo cp * /usr/local/impetus_lib/scripts
12. Spawn hazelcast instance: sudo -bE /usr/local/impetus_lib/scripts/start-hazelcast.sh

NOTE:Step 7-12 need to be executed on each node of hazelcast cluster.

Steps to Load data in Hazelcats cluster
----------------------------------------

1. Add the map's information in "hazelcast.xml".We have added one test map conf in it `map name="testMap"`.
2. Write loader class to load data in Hazelcats map. We have provided one Test class to add dummy 1k records in Hazelcast. Check HazelcastMapLoader.java to create your own loader.
3. Run HazelcastMapLoader.java class to ingest records in "testMap". To do this:
   - Add hazelcast-cluster-startup-1.0.0.jar and lib present in target folder to classpath: CLASSPATH="`<path to jar>`/hazelcast-cluster-startup-1.0.0.jar:`<path to jar>`/lib/*"
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


Deployment Steps(Hazelcast UI Application)
----------------

Build WebServices
----------------------------------------

1. Checkout the code
2. cd $GIT_CLONE_DIR/HazelcastWebServices
3. update the "etl.cluster.nodes" property in src/main/resources/app-config.properties with list of hazelcast instances (you can create comma separated list of instances if there are more than one instance)
4. Multiple conf file can be added paralle to "app-config.properties" per the env e.g "prod-app-config.properties" for production env "preprod-app-config.properties" for preprod and "ocal-app-config.properties" for local env etc.
4. Build the code using command "mvn clean install"
5. Copy target/HazelcastWebServices.war in webapps dir of apache tomcat

Build UI APP
----------------------------------------

1. cd $GIT_CLONE_DIR/UI_Code
2. Remove the dir "node_modules" if it is present else ignore.
3. Update following properties in app/scripts/app.js
   i. Change localhost to <IP of the node> in app.js (UI_Code/app/script/)
4. Create a file setenv.sh in "Tomcat Home"/bin folder (if not already present).
5. Add below line to setenv.sh (it sets the environment where the code is being deployed)
   - JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF8 -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -DDEPLOY_ENV=`env`". Here `env` could be local/prod/preprod as per the prefix of filename "HazelcastWebServices/src/main/resources/local-app-config.properties". 

Start The Services
----------------------------------------

1. Start the Hazelcast cluster whose IPs were added in "HazelcastWebServices/src/main/resources/`env`-app-config.properties". Make sure that hazelcast cluster is up and running.
2. Start tomcat.
3. To start UI app, follow below steps steps by logging into "root" user and from dir "$GIT_CLONE_DIR/UI_Code"
   - npm install
   - npm install karma --save-dev
   - npm install karma-jasmine karma-chrome-launcher jasmine-core --save-dev
   - npm install -g grunt-cli
   - nohup grunt --force serve &
   - If you get an error "Cannot find module : sigmund" then run npm install sigmund followed by Step 5 again
4. Hazelcast UI can be accessed by url - http:`IP Address`:9000/#/hazelcast


Features
--------

Home Screen


![home](/images/Home_Screen.png)
***

List of Members

1. To see the data in map, Go to "Maps" and then to "testMap".Click on "Browse".
2. Add the key in it and click on "Browse" button. In "testMap" the keys are : Key1,Key2...Key999.
3. To clear the Map record from UI, click on "Clear" tab. It will not remove the data from Hazelcast, but will only clear the screen.

![members](/images/List_Members.png)
***

List of Maps


![maps](/images/List_Maps.png)
***

Map View

![mapview](/images/Map_Screen.png)
***

Map Browser View

![mapbrowser](/images/Map_Browser.png)
***


Improvements Needed
---------------------
- [ ] Need to modify name of hazelcast webservices jar
