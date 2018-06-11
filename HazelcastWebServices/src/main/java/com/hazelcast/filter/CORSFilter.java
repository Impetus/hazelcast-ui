/**
 * Name           : CORSFilter.java
 * Type           : JAVA
 * Purpose        :
 * Description    :
 * Mod Log
 * Date        By               Jira      Description
 * -----------   -----------------   ----------     ---------------
**/

package com.hazelcast.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Filter class.
 * @author Sourav Gulati
 * @Description This class is used to add CORS filter
 */
@Component
public class CORSFilter implements Filter {

	@Override
	public void destroy() {
	}

	/**
	 * Method to filter
	 */
	@Override
	public void doFilter(
			final ServletRequest req, final ServletResponse res,
			final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept");
		chain.doFilter(req, res);
	}
	/**
	 * @throws ServletException
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

}
