package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.util.List;

/**
 * An interface for database communication.
 *
 * @author Bruna Dujmović
 *
 */
public interface DAO {

	/**
	 * Returns a blog entry that has the specified {@code id}. If no such entry exists
	 * in the database, this method returns {@code null}.
	 * 
	 * @param id the id of the blog entry
	 * @return the blog entry or {@code null} if no such entry exists
	 * @throws DAOException if there's an error with retrieving the data
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Returns a list of all blog users.
	 *
	 * @return a list of all blog users
	 * @throws DAOException if there's an error with retrieving the data
	 */
	List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Returns a {@link BlogUser} of the specified nickname or {@code null} if no such
	 * user exists.
	 *
	 * @param nick the nickname of the user
	 * @return a {@link BlogUser} of the specified nickname or {@code null} if no such
     *         user exists
     * @throws DAOException if there's an error with retrieving the data
	 */
	BlogUser getBlogUser(String nick) throws DAOException;

    /**
     * Adds a new {@link BlogUser} to the database.
     *
     * @param user the {@link BlogUser} to add
     */
	void addBlogUser(BlogUser user);

    /**
     * Adds a new {@link BlogEntry} to the database.
     *
     * @param entry the {@link BlogUser} to add
     */
	void addBlogEntry(BlogEntry entry);

    /**
     * Adds a new {@link BlogComment} to the database.
     *
     * @param comment the {@link BlogComment} to add
     */
	void addBlogComment(BlogComment comment);
}