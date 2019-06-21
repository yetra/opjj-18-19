package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.util.Utility;

import javax.servlet.http.HttpServletRequest;

/**
 * Models a blog user registration form.
 *
 * @see AbstractForm
 * @author Bruna Dujmović
 */
public class RegistrationForm extends AbstractForm {

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The e-mail of the user.
     */
    private String email;

    /**
     * The nickname of the user.
     */
    private String nick;
    
    /**
     * The hex-encoded hash value obtained from the user's password.
     */
    private String passwordHash;

    /**
     * Creates a form based on the parameters obtained from the given {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest} containing the parameters
     */
    public void fromHttpRequest(HttpServletRequest req) {
        firstName = prepare(req.getParameter("firstName"));
        lastName = prepare(req.getParameter("lastName"));
        email = prepare(req.getParameter("email"));
        nick = prepare(req.getParameter("nick"));
        passwordHash = Utility.getDigestOf(
                prepare(req.getParameter("password")));
    }

    /**
     * Creates a form based on the given {@link BlogUser}.
     *
     * @param user the {@link BlogUser} containing the original data
     */
    public void fromBlogUser(BlogUser user) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        nick = user.getNick();
        passwordHash = user.getPasswordHash();
    }

    /**
     * Fills the given {@link BlogUser} with the data stored in this form. This method
     * should not be called if the form hasn't been validated and if it contains errors.
     *
     * @param user the {@link BlogUser} to fill
     */
    public void toBlogUser(BlogUser user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNick(nick);
        user.setPasswordHash(passwordHash);
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets this form's {@link #firstName} to the given value.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets this form's {@link #lastName} to the given value.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets this form's {@link #email} to the given value.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the nickname of the user.
     *
     * @return the nickname of the user
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets this form's {@link #nick} to the given value.
     *
     * @param nick the nickname to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Returns the hash of the user's password.
     *
     * @return the hash of the user's password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets this form's {@link #passwordHash} to the given value.
     *
     * @param passwordHash the password hash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public void validate() {
        super.validate();

        if (firstName.isEmpty()) {
            setError("firstName", "First name not given!");
        }

        if (lastName.isEmpty()) {
            setError("lastName", "Last name not given!");
        }

        if (email.isEmpty()) {
            setError("email", "E-mail not given!");
        } else if (!email.matches(Utility.EMAIL_REGEX)) {
            setError("email", "Invalid e-mail format!");
        } else if (email.length() > BlogUser.EMAIL_LENGTH) {
            setError("email", "Email cannot be longer than " +
                    BlogUser.EMAIL_LENGTH + " characters!");
        }

        if (nick.isEmpty()) {
            setError("nick", "Nickname not given!");
        } else if (nick.length() > BlogUser.NICK_LENGTH) {
            setError("nick", "Nickname cannot be longer than " +
                    BlogUser.NICK_LENGTH + " characters!");
        }
    }
}
