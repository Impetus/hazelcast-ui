/**
 * Custome exception class
 */
package com.impetus.hazelcast.exception;

/**
 * @author pushkin.gupta
 */
public class HazelcastStartUpException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Parametrized constructor
	 *@param errorMsg .
	 */
	public HazelcastStartUpException(final String errorMsg) {
		super(errorMsg);
	}
}
