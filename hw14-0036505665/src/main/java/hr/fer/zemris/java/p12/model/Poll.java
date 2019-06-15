package hr.fer.zemris.java.p12.model;

/**
 * This class models a poll to be used in the Polls database.
 *
 * @author Bruna Dujmović
 *
 */
public class Poll {

    /**
     * The ID of the poll.
     */
    private long ID;

    /**
     * The title of the poll.
     */
    private String title;

    /**
     * A message describing the poll.
     */
    private String message;

    /**
     * Constructs a {@link Poll} with the given data.
     *
     * @param ID the ID of the poll
     * @param title the title of the poll
     * @param message a message describing the poll
     */
    public Poll(long ID, String title, String message) {
        this.ID = ID;
        this.title = title;
        this.message = message;
    }

    /**
     * Returns the ID of the poll.
     *
     * @return the ID of the poll
     */
    public long getID() {
        return ID;
    }

    /**
     * Returns the title of the poll.
     *
     * @return the title of the poll
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a message describing the poll.
     *
     * @return a message describing the poll
     */
    public String getMessage() {
        return message;
    }
}
