package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.util.Utility;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Models a blog user login form.
 *
 * @author Bruna Dujmović
 */
public class LoginForm {

    /**
     * The nickname of the user.
     */

    private String nick;
    /**
     * The hex-encoded hash value obtained from the user's password.
     */

    private String passwordHash;

    /**
     * A map of errors that have occurred during login validation.
     * The keys should be error names, and the values should be error messages.
     */
    private Map<String, String> errors = new HashMap<>();

    /**
     * Returns the error message for the specified error or {@code null} if no such
     * rror exists.
     *
     * @param error the name of the error
     * @return the error message or {@code null} if no error exists
     */
    public String getError(String error) {
        return this.errors.get(error);
    }

    /**
     * Returns {@code true} if errors have occured during login validation.
     *
     * @return {@code true} if errors have occured during login validation
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Returns {@code true} if the specified error has occured during login validation.
     *
     * @param error the name of the error
     * @return {@code true }if the specified error has occured during login validation
     */
    public boolean hasError(String error) {
        return this.errors.containsKey(error);
    }

    /**
     * Creates a form based on the parameters obtained from the given {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest} containing the parameters
     */
    public void fromHttpRequest(HttpServletRequest req) {
        this.nick = prepare(req.getParameter("nick"));
        this.passwordHash = Utility.getDigestOf(
                prepare(req.getParameter("password")));
    }

    /**
     * Creates a form based on the given {@link BlogUser}.
     *
     * @param user the {@link BlogUser} containing the original data
     */
    public void fromBlogUser(BlogUser user) {
        this.nick = user.getNick();
        this.passwordHash = user.getPasswordHash();
    }

    /**
     * Fills the given {@link BlogUser} with the data stored in this form. This method
     * should not be called if the form hasn't been validated and if it contains errors.
     *
     * @param user the {@link BlogUser} to fill
     */
    public void toBlogUser(BlogUser user) {
        user.setNick(this.nick);
        user.setPasswordHash(this.passwordHash);
    }

    /**
     * A helper method for converting {@code null} strings into empty strings.
     *
     * @param s the string to convert
     * @return the given string if it isn't {@code null} or an empty string
     */
    private String prepare(String s) {
        return (s == null) ? "" : s.trim();
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

    /**
     * Adds an error of the given key and value to this form's {@link #errors} map.
     *
     * @param error the key of the error
     * @param message the value of the error
     */
    public void setError(String error, String message) {
        errors.put(error, message);
    }

}
