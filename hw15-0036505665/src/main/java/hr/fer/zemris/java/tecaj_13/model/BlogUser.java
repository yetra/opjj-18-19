package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Models a blog user that can login to the blog, create or edit blog entries, and post
 * comments.
 *
 * @author Bruna Dujmović
 *
 */
@NamedQuery(name="BlogUser.listAll", query = "SELECT u FROM BlogUser u")
public class BlogUser {

    /**
     * The ID of the user.
     */
    private Long id;

    /**
     * The fist name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The nickname of the user.
     */
    private String nick;

    /**
     * The e-mail of the user.
     */
    private String email;

    /**
     * A hex-encoded hash value obtained from the user's password
     */
    private String passwordHash;

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user
     */
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user
     */
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the nickname of the user.
     *
     * @return the nickname of the user
     */
    @Column(length = 100, unique = true, nullable = false)
    public String getNick() {
        return nick;
    }

    /**
     * Returns the e-mail of the user.
     *
     * @return the e-mail of the user
     */
    @Column(length = 100, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Returns the hex-encoded hash value obtained from the user's password.
     *
     * @return the hex-encoded hash value obtained from the user's password
     */
    @Column(length = 256, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the user's ID to the given value.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the user's first name to the given value.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name to the given value.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the user's nickname to the given value.
     *
     * @param nick the nickname to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Sets the user's e-mail to the given value.
     *
     * @param email the e-mail to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's password hash to the given value.
     *
     * @param passwordHash the password hash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
