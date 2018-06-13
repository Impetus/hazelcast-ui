package com.impetus.hazelcast;

import org.slf4j.Logger;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

/**
 * Component to start HazelCast server Instance of 3.4.6 version
 * @author sameena.parveen
 */
public final class StartHazelCastInstance {

	private static final Logger LOGGER = LogUtils
			.getLogger(StartHazelCastInstance.class);
	private static boolean isHazelcastStarted = false;

	/**
	 * Default constructor
	 */
	private StartHazelCastInstance() {
	}

	/**
	 * Main method.
	 * @param args
	 *            input args
	 */
	public static void main(final String[] args) {
		StartHazelCastInstance.startHazelCastInstance();
		if (isHazelcastStarted) {
			LOGGER.info("Hazelcast instance started successfully");
		}
	}

	/**
	 * Method to start Hazelcast instance and return true if it is started
	 * successfully else false.
	 * @return isHazelcastStarted
	 */
	public static boolean startHazelCastInstance() {

		try {
			final HazelcastInstance instance = Hazelcast.newHazelcastInstance();
			final Cluster cluster = instance.getCluster();

			cluster.addMembershipListener(new MembershipListener() {
				public void memberAdded(final MembershipEvent membersipEvent) {
					LOGGER.info("MemberAdded " + membersipEvent);
				}

				public void memberRemoved(
						final MembershipEvent membersipEvent) {
					LOGGER.info("MemberRemoved " + membersipEvent);
				}

				@Override
				public void memberAttributeChanged(
						final MemberAttributeEvent memberAttributeEvent) {

				}
			});
			isHazelcastStarted = true;

		} catch (final Exception e) {
			LOGGER.error(
					"Error encountered while initiating hazelcast instance ",
					e);
			isHazelcastStarted = false;
		}

		return isHazelcastStarted;
	}

}
