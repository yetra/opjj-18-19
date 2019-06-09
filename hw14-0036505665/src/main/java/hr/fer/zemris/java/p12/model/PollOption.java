package hr.fer.zemris.java.p12.model;

/**
 * This class models a poll option entry to be used in the PollOptions database.
 *
 * @author Bruna Dujmović
 *
 */
public class PollOption implements Comparable<PollOption> {

    /**
     * The title of the option.
     */
    private String optionTitle;

    /**
     * The option's link.
     */
    private String optionLink;

    /**
     * The number of votes that the option received.
     */
    private int votesCount;

    /**
     * Constructs a {@link PollOption} of the given attributes.
     *
     * @param title the title of the option
     * @param link the option's link
     * @param votesCount the number of votes that the option received
     */
    public PollOption(String title, String link, int votesCount) {
        this.optionTitle = title;
        this.optionLink = link;
        this.votesCount = votesCount;
    }

    /**
     * Returns the title of the option.
     *
     * @return the title of the option
     */
    public String getOptionTitle() {
        return optionTitle;
    }

    /**
     * Returns the option's link.
     *
     * @return the option's link
     */
    public String getOptionLink() {
        return optionLink;
    }

    /**
     * Returns the number of votes that the option received.
     *
     * @return the number of votes that the option received
     */
    public int getVotesCount() {
        return votesCount;
    }

    @Override
    public int compareTo(PollOption o) {
        return Integer.compare(this.votesCount, o.votesCount);
    }
}
