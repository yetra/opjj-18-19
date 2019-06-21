package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * An implementation of the {@link DAO} interface using the Java Persistance API.
 *
 * @author Bruna Dujmović
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public List<BlogUser> getBlogUsers() {
		return JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.listAll", BlogUser.class)
				.getResultList();
	}

	@Override
	public BlogUser getBlogUser(String nick) {
		try {
			return JPAEMProvider.getEntityManager()
					.createNamedQuery("BlogUser.getByNick", BlogUser.class)
					.setParameter("nick", nick)
					.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<BlogEntry> getBlogEntriesFor(String nick) {
		return null;
	}

	@Override
	public void addBlogUser(BlogUser user) {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public void addBlogEntry(BlogEntry entry) {
		EntityManager em = JPAEMProvider.getEntityManager();

		if (entry.getId() == null) {
			em.persist(entry);
		} else {
			em.merge(entry);
		}
	}

	@Override
	public void addBlogComment(BlogComment comment) {
		EntityManager em = JPAEMProvider.getEntityManager();

		if (comment.getId() == null) {
			em.persist(comment);
		} else {
			em.merge(comment);
		}
	}
}