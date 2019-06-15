package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.util.List;

/**
 * An interface for database communication.
 *
 * @author Bruna Dujmović
 *
 */
public interface DAO {

    /**
     * Returns a list of polls found in the database.
     *
     * @return a list of polls found in the database
     */
    List<Poll> getPolls();

    /**
     * Returns a list of poll options retrieved from a poll specified by ID.
     *
     * @param pollID the ID of the poll
     * @return a list of poll options retrieved from a poll specified by ID
     */
    List<PollOption> getPollOptionsFor(long pollID);

    /**
     * Returns the data of a specified poll.
     *
     * @param pollID the ID of the poll
     * @return the data of a specified poll
     */
    Poll getPollDataFor(long pollID);

    /**
     * Increments the votes count for a specified poll option.
     *
     * @param ID the ID of the poll option
     */
    void updateScoreFor(long ID);
	
}