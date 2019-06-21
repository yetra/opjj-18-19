package hr.fer.zemris.java.tecaj_13.dao;

/**
 * An exception to be thrown when there is an issue with database communication.
 */
public class DAOException extends RuntimeException {

	/**
	 * The version number of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link DAOException} with the given parameters.
	 *
	 * @param message a message about the exception
	 * @param cause the cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new {@link DAOException} containing the given message.
	 *
	 * @param message a message about the exception
	 */
	public DAOException(String message) {
		super(message);
	}
}