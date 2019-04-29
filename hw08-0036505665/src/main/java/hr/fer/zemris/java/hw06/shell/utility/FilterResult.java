package hr.fer.zemris.java.hw06.shell.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterResult {

    /**
     * The name of the result.
     */
    private String name;

    /**
     * The {@link Matcher} object constructed for this filter result's name.
     */
    private Matcher matcher;

    /**
     * Constructs a {@link FilterResult} object for a given file name that matches a
     * specified pattern.
     *
     * @param name the name of the result
     * @param pattern the pattern that the name should match
     * @throws IllegalArgumentException if the given name cannot be matched by the
     *         specified pattern
     */
    public FilterResult(String name, String pattern) {
        this.name = name;
        this.matcher = Pattern.compile(pattern).matcher(name);

        if (!matcher.find()) {
            throw new IllegalArgumentException("No matches found in name \"" + name + "\".");
        }
    }

    /**
     * Returns the number of groups found in a given file name, ignoring the implicit
     * group 0 that contains the entire name.
     *
     * @return the number of groups found in a given file name
     */
    public int numberOfGroups() {
        return matcher.groupCount();
    }

    /**
     * Returns the group specified by a given index. Group 0 is the implicit group that
     * contains the entire file name. Subsequent groups are indexed from left to right,
     * starting from 1.
     *
     * @param index the index of the group to return
     * @return the group specified by the given index
     * @throws IllegalArgumentException if the given index is not in range [0, {@link #numberOfGroups()}]
     */
    public String group(int index) {
        if (index < 0 || index > numberOfGroups()) {
            throw new IndexOutOfBoundsException(
                    "Group index not in range [0, " + numberOfGroups() + "].");
        }

        return matcher.group(index);
    }


    @Override
    public String toString() {
        return name;
    }
}
