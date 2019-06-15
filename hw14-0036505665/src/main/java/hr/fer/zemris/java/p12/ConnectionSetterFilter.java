package hr.fer.zemris.java.p12;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 * A filter that sets the {@link SQLConnectionProvider}'s connection to a thread
 * retrieved from the connection pool.
 */
@WebFilter("/servleti/*")
public class ConnectionSetterFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		
		DataSource ds = (DataSource) request.getServletContext().getAttribute(
				"hr.fer.zemris.dbpool"
		);

		try (Connection con = ds.getConnection()) {
			SQLConnectionProvider.setConnection(con);

			try {
				chain.doFilter(request, response);
			} finally {
				SQLConnectionProvider.setConnection(null);
			}

		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
	}
	
}