package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.util.Utility;

import javax.servlet.http.HttpServletRequest;

/**
 * Models a blog comment form.
 *
 * @see AbstractForm
 * @author Bruna Dujmović
 */
public class BlogCommentForm extends AbstractForm {

    /**
     * This form's comment e-mail.
     */
    private String usersEMail;

    /**
     * The message of this comment form.
     */
    private String message;

    /**
     * Creates a form based on the parameters obtained from the given {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest} containing the parameters
     */
    public void fromHttpRequest(HttpServletRequest req) {
        usersEMail = prepare(req.getParameter("usersEMail"));
        message = prepare(req.getParameter("message"));
    }

    /**
     * Creates a form based on the given {@link BlogComment}.
     *
     * @param comment the {@link BlogComment} containing the original data
     */
    public void fromBlogComment(BlogComment comment) {
        usersEMail = comment.getUsersEMail();
        message = comment.getMessage();
    }

    /**
     * Fills the given {@link BlogComment} with the data stored in this form. This method
     * should not be called if the form hasn't been validated and if it contains errors.
     *
     * @param comment the {@link BlogComment} to fill
     */
    public void toBlogComment(BlogComment comment) {
        comment.setUsersEMail(usersEMail);
        comment.setMessage(message);
    }

    /**
     * Returns this form's comment e-mail.
     *
     * @return this form's comment e-mail
     */
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Returns the message of this comment form.
     *
     * @return the message of this comment form
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets this form's comment e-mail to the given value.
     *
     * @param usersEMail the user email to set
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Sets the message of this comment form to the given value.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void validate() {
        super.validate();

        if (usersEMail.isEmpty()) {
            setError("usersEMail", "E-mail not given!");
        } else if (!usersEMail.matches(Utility.EMAIL_REGEX)) {
            setError("usersEMail", "Invalid e-mail format!");
        } else if (usersEMail.length() > BlogUser.EMAIL_LENGTH) {
            setError("usersEMail", "Email cannot be longer than " +
                    BlogUser.EMAIL_LENGTH + " characters!");
        }

        if (message.isEmpty()) {
            setError("message", "Message not given!");
        } else if (message.length() > BlogComment.MESSAGE_LENGTH) {
            setError("message", "Message cannot be longer than " +
                    BlogComment.MESSAGE_LENGTH + " characters!");
        }
    }
}
