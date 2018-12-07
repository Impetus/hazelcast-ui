# Building Hazelcast-ui components
There are two components of hazlecst-ui i.e. web service and ui. Follows below steps to build them:

## Building Hazelcast Webservices
Pre-requisite to starting Hazelcast Webservices component is that the Hazelcast cluster must be up and running. In case you do not have an existing hazelcast cluster, please follow ![Starting Hazelcast Cluster](starting_hazelcast.md). 
Hazelcast Webservices work by connecting to a hazelcast cluster and provide a wrapper layer to access hazelcast api's. Follow below steps to build and deploy Hazelcast Webservices.
- Checkout the code from ![here](https://github.com/Impetus/hazelcast-ui/tree/master/HazelcastWebServices).
- Go to the webservices folder - `cd $GIT_CLONE_DIR/HazelcastWebServices`.
- Update the `etl.cluster.nodes` property in `src/main/resources/app-config.properties` with list of hazelcast instances (you can create comma separated list of instances if there are more than one instance).Make sure to prepend the env in `app-config.properties` e.g for local the filename should be `local-app-config.properties`. Similarly for other envs.
- Multiple conf file can be added parallel to `<env>-app-config.properties` per env e.g `prod-app-config.properties` for production env `preprod-app-config.properties` for preprod and `local-app-config.properties` for local env etc.
- Build the code using command `mvn clean install`.

## Deploying Hazelcast Webservices
Follow below steps for deploying and starting hazelcast webservice
- Go to target directory and copy `HazelcastWebServices.war` to webapps dir of Apache Tomcat.
- Once tomcat server is up and running, Hazelcast webservices are also up. Open browser and go to url - `http:<IP Address>:<tomcat port>/HazelcastWebServices/`
- You will see a message "Welcome to Hazelcast Web UI" indicating that webservices have got started successfully.

## Building, deploying and starting Hazelcast UI APP
Follow below steps for building, deploying, starting hazelcast ui
- Checkout the code from ![here](https://github.com/Impetus/hazelcast-ui/tree/master/UI_Code).
- Go to UI directory - `cd $GIT_CLONE_DIR/UI_Code`.
- Check for presence of `node_modules` directory. If present delete it otherwise go to next step.
- Go to `app/scripts` directory and edit `app.js`
   - Change `localhost` to `IP of the node`.
- Go to `Apache Tomcat installation directory`. Navigate to `bin` directory and create a file `setenv.sh` (if not already present).
- `setenv.sh` is used to set the environment where the code is being deployed. Add below line at the end of file:
   - `JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF8 -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -DDEPLOY_ENV=`env`". Here `env` could be local/prod/preprod as per the prefix of filename "HazelcastWebServices/src/main/resources/local-app-config.properties"`. 
- Start tomcat server.
- Open terminal, log in as `root` user and navigate to `$GIT_CLONE_DIR/UI_Code` directory. Run below commands:
  ```
   - npm install
   - npm install karma --save-dev
   - npm install karma-jasmine karma-chrome-launcher jasmine-core --save-dev
   - npm install -g grunt-cli
   - nohup grunt --force serve &
  ```
  If you get an error `Cannot find module : sigmund` then run `npm install sigmund` followed by `nohup grunt --force serve &` again.
- Once above steps have been run successfully Hazelcast UI is up and running. It can be accessed at url - `http:<IP Address>:9000/#/hazelcast`
