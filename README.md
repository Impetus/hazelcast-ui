Hazelcast UI APP
=============

Hazelcast UI provided in development version is restricted to two nodes.This APP provides basic capabilites of Hazelcast UI with no restriction on number of nodes.

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

Deployment Steps
----------------

Build WebServices

1. Checkout the code
2. cd $GIT_CLONE_DIR/HazelcastWebServices
3. update the "etl.cluster.nodes" property in src/main/resources/app-config.properties with list of hazelcast instances (you can create comma separated list of instances if there are more than one instance)
4. Multiple conf file can be added paralle to "app-config.properties" per the env e.g "prod-app-config.properties" for production env "preprod-app-config.properties" for preprod and "ocal-app-config.properties" for local env etc.
4. Build the code using command "mvn clean install"
5. Copy target/HazelcastWebServices.war in webapps dir of apache tomcat

Build UI APP

1. cd $GIT_CLONE_DIR/UI_Code
2. Remove the dir "node_modules" if it is present else ignore.
3. Update following properties in app/scripts/app.js
   i. Change localhost to <IP of the node> in app.js (UI_Code/app/script/)
4. Create a file setenv.sh in "Tomcat Home"/bin folder (if not already present).
5. Add below line to setenv.sh (it sets the environment where the code is being deployed)
JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF8 -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -DDEPLOY_ENV=<env>". Here <env> could be local/prod/preprod as per the prefix of filename "HazelcastWebServices/src/main/resources/local-app-config.properties". 

Start The Services

1. Start the Hazelcast cluster whose IPs were added in "HazelcastWebServices/src/main/resources/<env>-app-config.properties". Make sure that hazelcast cluster is up and running.
2. Start tomcat.
3. To start UI app, follow below steps steps by logging into "root" user and from dir "$GIT_CLONE_DIR/UI_Code"
    i. npm install
    ii. npm install karma --save-dev
    iii. npm install karma-jasmine karma-chrome-launcher jasmine-core --save-dev
    iv. npm install -g grunt-cli
    v. nohup grunt --force serve &
    
4. hazelcast UI can be accessed by url - http://<IP Address>:9000/#/hazelcast


Features
--------

Home Screen


![home](/images/Home_Screen.png)
***

List of Members


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
1. Need to add proper loggers in webservices
2. Need to add generic error messages in web services 
