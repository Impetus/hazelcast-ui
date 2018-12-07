#  Troubleshooting
Follow below steps if in any case the Hazelcast Custom UI does not show cluster(memory utilization) or member information.
- Check if Hazelcast cluster is up and running. If yes then simply restart Tomcat service.
- If Hazelcast cluster is down then first start hazelcast cluster followed by restart of Tomcat service.
- If `npm install` gives certificate issue then execute command `npm config set strict-ssl false` from `$GIT_CLONE_DIR/UI_Code` directory.
- If unable to find Node during the command `npm install`. The issue is due to the name difference. As in system it is nodejs while code is looking for node. To resolve the issue run command `sudo ln -s /usr/bin/nodejs /usr/bin/node`. It will lync nodejs with node.
- If one faces permission issue due to the lock on folder in UI code. The execute command `sudo chmod 777 <path> -R` to resolve the issue.
