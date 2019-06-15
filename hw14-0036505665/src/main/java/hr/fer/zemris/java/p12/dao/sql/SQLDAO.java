package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the database communication interface {@link DAO}. This class
 * expects to receive the database connection through the {@link SQLConnectionProvider}
 * class.
 *  
 * @author Bruna Dujmović
 * 
 */
public class SQLDAO implements DAO {

    @Override
    public List<Poll> getPolls() {
        List<Poll> polls = new ArrayList<>();
        Connection conn = SQLConnectionProvider.getConnection();

        PreparedStatement pst;
        try {
            pst = conn.prepareStatement("SELECT id, title, message FROM Polls ORDER BY id");

            try {
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    Poll poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
                    polls.add(poll);
                }

            } finally {
                try {
                    pst.close();
                } catch (Exception ignorable) {}
            }

        } catch (Exception ex) {
            throw new DAOException("Cannot get poll list!", ex);
        }

        return polls;
    }

    @Override
    public List<PollOption> getPollOptionsFor(long pollID) {
        List<PollOption> options = new ArrayList<>();
        Connection conn = SQLConnectionProvider.getConnection();

        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(
                    "SELECT id, optionTitle, optionLink, votesCount FROM PollOptions WHERE pollID = ? ORDER BY id"
            );
            pst.setLong(1, pollID);

            try {
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    options.add(new PollOption(
                            rs.getLong(1), rs.getString(2),
                            rs.getString(3), rs.getInt(4)
                    ));
                }

            } finally {
                try {
                    pst.close();
                } catch (Exception ignorable) {}
            }

        } catch (Exception ex) {
            throw new DAOException("Cannot get poll option list!", ex);
        }

        return options;
    }

    @Override
    public Poll getPollDataFor(long pollID) {
        Connection conn = SQLConnectionProvider.getConnection();
        Poll poll = null;

        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(
                    "SELECT title, message FROM Polls WHERE ID = ?"
            );
            pst.setLong(1, pollID);

            try {
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    poll = new Poll(pollID, rs.getString(1), rs.getString(2));
                }

            } finally {
                try {
                    pst.close();
                } catch (Exception ignorable) {}
            }

        } catch (Exception ex) {
            throw new DAOException("Cannot get poll data!", ex);
        }

        return poll;
    }

    @Override
    public void updateScoreFor(long ID) {
        Connection conn = SQLConnectionProvider.getConnection();

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE PollOptions SET votesCount = votesCount + 1 WHERE ID = ?");
            pst.setLong(1, ID);
            pst.executeUpdate();

        } catch(Exception ex) {
            throw new DAOException("Cannot get poll data!", ex);

        } finally {
            try { pst.close(); } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}