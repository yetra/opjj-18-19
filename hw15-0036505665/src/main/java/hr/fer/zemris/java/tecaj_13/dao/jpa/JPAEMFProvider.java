package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * A class for retrieving the {@link EntityManagerFactory}.
 */
public class JPAEMFProvider {

	/**
	 * The {@link EntityManagerFactory} to provide.
	 */
	public static EntityManagerFactory emf;

	/**
	 * Returns the {@link EntityManagerFactory}.
	 *
	 * @return the {@link EntityManagerFactory} to provide
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the {@link EntityManagerFactory} to the given value.
	 *
	 * @param emf the new {@link EntityManagerFactory} to provide
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}