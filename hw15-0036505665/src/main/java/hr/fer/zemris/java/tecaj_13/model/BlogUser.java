package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.*;
import java.util.List;

/**
 * Models a blog user that can login to the blog, create or edit blog entries, and post
 * comments.
 *
 * @author Bruna Dujmović
 *
 */
@Entity
@Table(name = "blog_users")
@NamedQuery(name="BlogUser.listAll", query = "SELECT u FROM BlogUser u")
@NamedQuery(name="BlogUser.getByNick", query = "SELECT u FROM BlogUser u WHERE u.nick =: nick")
public class BlogUser {

    /**
     * The ID of this user.
     */
    private Long id;

    /**
     * The fist name of this user.
     */
    private String firstName;

    /**
     * The last name of this user.
     */
    private String lastName;

    /**
     * The nickname of this user.
     */
    private String nick;

    /**
     * The e-mail of this user.
     */
    private String email;

    /**
     * A hex-encoded hash value obtained from this user's password
     */
    private String passwordHash;

    /**
     * A list of blog entries that this user has created.
     */
    private List<BlogEntry> entires;

    /**
     * Returns the ID of this user.
     *
     * @return the ID of this user
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Returns the first name of this user.
     *
     * @return the first name of this user
     */
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of this user.
     *
     * @return the last name of this user
     */
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the nickname of this user.
     *
     * @return the nickname of this user
     */
    @Column(length = 100, unique = true, nullable = false)
    public String getNick() {
        return nick;
    }

    /**
     * Returns the e-mail of this user.
     *
     * @return the e-mail of this user
     */
    @Column(length = 100, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Returns the hex-encoded hash value obtained from this user's password.
     *
     * @return the hex-encoded hash value obtained from this user's password
     */
    @Column(length = 256, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Returns a list of blog entries that this user has created.
     *
     * @return a list of blog entries that this user has created.
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    public List<BlogEntry> getEntires() {
        return entires;
    }

    /**
     * Sets this user's ID to the given value.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets this user's first name to the given value.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets this user's last name to the given value.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets this user's nickname to the given value.
     *
     * @param nick the nickname to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Sets this user's e-mail to the given value.
     *
     * @param email the e-mail to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets this user's password hash to the given value.
     *
     * @param passwordHash the password hash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Sets this user's entries list to the given value.
     *
     * @param entires the entries list to set
     */
    public void setEntires(List<BlogEntry> entires) {
        this.entires = entires;
    }
}
