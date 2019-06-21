package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Returns a {@link DAO} object for database communication.
 */
public class DAOProvider {

	/**
	 * An instance of the {@link DAO} object.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Returns the {@link DAO} object for database communication.
	 *
	 * @return the {@link DAO} object for database communication
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}