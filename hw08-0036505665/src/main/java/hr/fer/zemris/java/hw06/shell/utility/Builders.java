package hr.fer.zemris.java.hw06.shell.utility;

/**
 * A class containing the default {@link NameBuilder}s for the massrename command.
 *
 * @author Bruna Dujmović
 *
 */
public class Builders {

    /**
     * A {@link NameBuilder} that appends a given string constant to the given
     * {@link StringBuilder} object.
     *
     * @param text the string constant to append
     * @return a {@link NameBuilder} that appends a given string constant to the given
     *         {@link StringBuilder} object
     */
    static NameBuilder text(String text) {
        return (result, sb) -> sb.append(text);
    }

    /**
     * A {@link NameBuilder} that appends a capturing group on the specified index to
     * the given {@link StringBuilder} object.
     *
     * @param index the index of the capturing group
     * @return a {@link NameBuilder} that appends a capturing group on the specified
     *         index to the given {@link StringBuilder} object.
     */
    static NameBuilder group(int index) {
        return (result, sb) -> sb.append(result.group(index));
    }

    /**
     * A {@link NameBuilder} that appends a capturing group on the specified index to
     * the given {@link StringBuilder} object. The group string will be at least of
     * the specified {@code minWidth} and left-padded with the specified {@code padding}.
     *
     * @param index the index of the capturing group
     * @return a {@link NameBuilder} that appends a capturing group on the specified
     *         index to the given {@link StringBuilder} object.
     */
    static NameBuilder group(int index, char padding, int minWidth) {
        return (result, sb) -> {
            String group = result.group(index);
            int lengthDiff = minWidth - group.length();

            sb.append(lengthDiff > 0 ?
                    String.valueOf(padding).repeat(lengthDiff) + group :
                    group);
        };
    }
}
