/**
 * 
 */
package com.impetus.hazelcast.exception;

/**
 * @author pushkin.gupta
 *
 */
public class HazelcastStartUpException extends RuntimeException {

	public HazelcastStartUpException(String errorMsg) {
		super(errorMsg);
	}
}
