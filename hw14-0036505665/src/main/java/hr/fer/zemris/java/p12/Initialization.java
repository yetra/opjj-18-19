package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Initializes and terminates the app when the appropriate {@link ServletContextEvent}
 * occurs.
 */
@WebListener
public class Initialization implements ServletContextListener {

	/**
	 * The path to the database URL .properties file.
	 */
	private static final String DB_SETTINGS_PATH = "/WEB-INF/dbsettings.properties";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String realPath = sce.getServletContext().getRealPath(DB_SETTINGS_PATH);
		String connectionURL = getConnectionURL(realPath);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException("Pool initialization error!", e);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)
				sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");

		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the database connection URL.
	 *
	 * @param path a path to the .properties file containing URL data
	 * @return the database connection URL
	 */
	private static String getConnectionURL(String path) {
		try {
			InputStream config = Files.newInputStream(Paths.get(path));
			Properties properties = new Properties();
			properties.load(config);

			String host = properties.getProperty("host");
			String port = properties.getProperty("port");
			String name = properties.getProperty("name");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");

			if (host == null || port == null || name == null || user == null
					|| password == null) {
				throw new IllegalArgumentException("Invalid database settings!");
			}

			return "jdbc:derby://" + host + ":" + port + "/" + name + ";user="
					+ user + ";password=" + password;

		} catch (IOException e) {
			throw new RuntimeException("Invalid .properties file!");
		}
	}

}