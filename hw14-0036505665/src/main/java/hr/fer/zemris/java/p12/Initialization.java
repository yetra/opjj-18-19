package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.p12.model.PollOption;
import hr.fer.zemris.java.p12.model.PollOptions;

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

    /**
     * The path to the band data file.
     */
	private static final String BAND_DATA = "/WEB-INF/band-data.txt";

    /**
     * The path to the animal data file.
     */
	private static final String ANIMAL_DATA = "/WEB-INF/animal-data.txt";

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

		try (Connection conn = cpds.getConnection()) {
			createTables(conn);
			String bandData = sce.getServletContext().getRealPath(BAND_DATA);
			String animalData = sce.getServletContext().getRealPath(ANIMAL_DATA);
			addDefaultPolls(conn, bandData, animalData);
		} catch (Exception e) {
            e.printStackTrace();
		}

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

	/**
	 * Creates the Polls and PollOptions tables if they don't already exist in the
     * database.
	 *
	 * @param conn a connection with the database
	 * @throws SQLException if there was an issue with the database
	 */
	private void createTables(Connection conn) throws SQLException {
		DatabaseMetaData databaseMetaData = conn.getMetaData();

        PreparedStatement pst = null;

        try (ResultSet rset = databaseMetaData.getTables(null, null, "POLLS", null)) {
            if (!rset.next()) {
                pst = conn.prepareStatement(
                        "CREATE TABLE Polls\n" +
                        " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                        " title VARCHAR(150) NOT NULL,\n" +
                        " message CLOB(2048) NOT NULL\n" +
                        ")");
                pst.executeUpdate();
            }
        }

		try (ResultSet rset = databaseMetaData.getTables(null, null, "POLLOPTIONS", null)) {
			if (!rset.next()) {
                pst = conn.prepareStatement(
                        "CREATE TABLE PollOptions\n" +
                        " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                        " optionTitle VARCHAR(100) NOT NULL,\n" +
                        " optionLink VARCHAR(150) NOT NULL,\n" +
                        " pollID BIGINT,\n" +
                        " votesCount BIGINT,\n" +
                        " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" +
                        ")");
				pst.executeUpdate();
				pst.close();
			}
		}
	}

    /**
     * Inserts default data into the PollOptions table.
     *
     * @param conn a connection with the database
     * @param bandData a file specifying the band default poll data
     * @param animalData a file specifying the animal default poll data
     * @throws SQLException if there was an issue with the database
     */
    private void addDefaultPolls(Connection conn, String bandData, String animalData)
            throws SQLException {

        if (isPollsEmpty(conn)) {
            createPoll("Glasanje za omiljeni bend:",
                    "Od sljedećih bendova, koji Vam je bend najdraži? " +
                            "Kliknite na link kako biste glasali!",
                    PollOptions.fromFile(Paths.get(bandData)), conn);

            createPoll("Glasanje za omiljenu životinju:",
                    "Od sljedećih životinja, koji Vam je životinja najdraža? " +
                            "Kliknite na link kako biste glasali!",
                    PollOptions.fromFile(Paths.get(animalData)), conn);
        }
    }

	/**
	 * Returns {@code true} if the Polls table is empty.
	 *
	 * @param conn a connection with the database
	 * @return {@code true} if the Polls table is empty
	 * @throws SQLException if there was an issue with the database
	 */
	private boolean isPollsEmpty(Connection conn) throws SQLException {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Polls");
		ResultSet rset = pst.executeQuery();

		return rset == null || !rset.next();
	}

	/**
	 * Creates a poll of the specified title, message, and poll options if it doesn't
	 * already exist in the database.
	 *
	 * @param title the title of the poll
	 * @param message the message of the poll
	 * @param options the poll options
	 * @param conn a connection to the database storing the polls
	 * @throws SQLException if there was an issue with the database
	 */
	private void createPoll(String title, String message, List<PollOption> options,
							Connection conn) throws SQLException {

		PreparedStatement pst = conn.prepareStatement(
				"INSERT INTO Polls (title, message) values (?,?)",
				Statement.RETURN_GENERATED_KEYS
		);

		pst.setString(1, title);
		pst.setString(2, message);
		pst.executeUpdate();

		try (ResultSet rset = pst.getGeneratedKeys()) {
			if (rset != null && rset.next()) {
				long pollID = rset.getLong(1);

				pst = conn.prepareStatement(
						"INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)"
				);

				for (PollOption option : options) {
					pst.setString(1, option.getOptionTitle());
					pst.setString(2, option.getOptionLink());
					pst.setLong(3, pollID);
					pst.setInt(4, option.getVotesCount());
					pst.executeUpdate();
				}
			}
		}
	}
}