package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.util.Utility;

import javax.servlet.http.HttpServletRequest;

/**
 * Models a blog user login form.
 *
 * @author Bruna Dujmović
 */
public class LoginForm extends AbstractForm {

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

}
