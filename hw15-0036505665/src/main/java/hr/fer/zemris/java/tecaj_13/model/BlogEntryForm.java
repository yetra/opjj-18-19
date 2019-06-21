package hr.fer.zemris.java.tecaj_13.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Models a blog entry form.
 *
 * @see AbstractForm
 * @author Bruna Dujmović
 */
public class BlogEntryForm extends AbstractForm {

    /**
     * The title of this entry form.
     */
    private String title;

    /**
     * The text of this entry form.
     */
    private String text;

    /**
     * Creates a form based on the parameters obtained from the given {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest} containing the parameters
     */
    public void fromHttpRequest(HttpServletRequest req) {
        title = prepare(req.getParameter("title"));
        text = prepare(req.getParameter("text"));
    }

    /**
     * Creates a form based on the given {@link BlogEntry}.
     *
     * @param entry the {@link BlogEntry} containing the original data
     */
    public void fromBlogEntry(BlogEntry entry) {
        title = entry.getTitle();
        text = entry.getText();
    }

    /**
     * Fills the given {@link BlogEntry} with the data stored in this form. This method
     * should not be called if the form hasn't been validated and if it contains errors.
     *
     * @param entry the {@link BlogEntry} to fill
     */
    public void toBlogEntry(BlogEntry entry) {
        entry.setTitle(title);
        entry.setText(text);
    }

    /**
     * Returns the title of this entry form.
     *
     * @return the title of this entry form.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the text of this entry form.
     *
     * @return the text of this entry form
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the title of this entry form to the given value.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the text of this entry form to the given value.
     *
     * @param text the title to set
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void validate() {
        super.validate();

        if (title.isEmpty()) {
            setError("title", "Title not given!");
        } else if (title.length() > BlogEntry.TITLE_LENGTH) {
            setError("title", "Title cannot be longer than " +
                    BlogEntry.TITLE_LENGTH + " characters!");
        }

        if (text.isEmpty()) {
            setError("text", "Text not given!");
        } else if (text.length() > BlogEntry.TEXT_LENGTH) {
            setError("text", "Text cannot be longer than " +
                    BlogEntry.TEXT_LENGTH + " characters!");
        }
    }
}