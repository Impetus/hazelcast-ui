package com.impetus.hazelcast;

import org.slf4j.Logger;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import com.impetus.hazelcast.LogUtils;

/**
 * Component to start HazelCast server Instance of 3.4.6 version
 * @author sameena.parveen
 */
public class StartHazelCastInstance {

	private static final Logger logger = LogUtils
			.getLogger(StartHazelCastInstance.class);
	private static boolean isHazelcastStarted=false;

	public static void main(String args[]) {
		new StartHazelCastInstance().startHazelCastInstance();
		if(isHazelcastStarted)
		logger.info("Hazelcast instance started successfully");
		
	}
	/**
	 * Method to start Hazelcast instance and return true if it is started successfully 
	 * else false
	 */
	public static boolean startHazelCastInstance(){

		
		try {
			HazelcastInstance instance = Hazelcast.newHazelcastInstance();
			Cluster cluster = instance.getCluster();

			cluster.addMembershipListener(new MembershipListener() {
				public void memberAdded(MembershipEvent membersipEvent) {
					logger.info("MemberAdded " + membersipEvent);
				}

				public void memberRemoved(MembershipEvent membersipEvent) {
					logger.info("MemberRemoved " + membersipEvent);
				}

				@Override
				public void memberAttributeChanged(
						MemberAttributeEvent memberAttributeEvent) {

				}
			});
			isHazelcastStarted=true;

		} catch (Exception e) {
			logger.error(
					"Error encountered while initiating hazelcast instance ", e);
			isHazelcastStarted=false;
		}

	return isHazelcastStarted;
		
	}

}
