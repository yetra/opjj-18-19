package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

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
			List<String> lines = Files.readAllLines(Paths.get(path));

			return "jdbc:derby://" + getDBSetting("host", lines.get(0))
					+ ":" + getDBSetting("port", lines.get(1))
					+ "/" + getDBSetting("name", lines.get(2))
					+ ";user=" + getDBSetting("user", lines.get(3))
					+ ";password=" + getDBSetting("password", lines.get(4));

		} catch (IOException e) {
			throw new RuntimeException("Invalid .properties file!");
		}
	}

	/**
	 * Returns the value of a database URL setting specified by a given name.
	 *
	 * @param name the name of the setting
	 * @param line the string containing the setting
	 * @return the value of a database URL setting specified by a given name
	 */
	private static String getDBSetting(String name, String line) {
		String[] parts = line.split("=");

		if (!parts[0].equals(name)) {
			throw new IllegalArgumentException("Invalid entry for " + name + "!");
		}

		return parts[1];
	}

}