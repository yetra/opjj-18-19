package hr.fer.zemris.java.util;

import java.util.Objects;

/**
 * A container class for all band data.
 *
 * @author Bruna Dujmović
 */
public class BandData implements Comparable<BandData> {

    /**
     * The ID of the band.
     */
    private int ID;

    /**
     * The name of the band.
     */
    private String name;

    /**
     * A link to one of the band's songs.
     */
    private String link;

    /**
     * The number of votes that the band received.
     */
    private int score;

    /**
     * Constructs a {@link BandData} container object consisting of the given data.
     *
     * @param ID the ID of the band
     * @param band the name of the band
     * @param link a link to one of the band's songs
     * @param score the number of votes that the band received
     */
    public BandData(int ID, String band, String link, int score) {
        this.ID = ID;
        this.name = band;
        this.link = link;
        this.score = score;
    }

    /**
     * Returns the ID of the band.
     *
     * @return the ID of the band
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the name of the band.
     *
     * @return the name of the band
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a link to one of the band's songs.
     *
     * @return a link to one of the band's songs
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns the number of votes that the band received.
     *
     * @return the number of votes that the band received
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the number of votes that the band received to the given score.
     *
     * @param score the new number of votes for the band
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BandData that = (BandData) o;
        return ID == that.ID &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }

    @Override
    public int compareTo(BandData o) {
        return Integer.compare(this.score, o.score);
    }
}
