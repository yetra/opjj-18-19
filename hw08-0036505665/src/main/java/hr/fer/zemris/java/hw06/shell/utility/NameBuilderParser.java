package hr.fer.zemris.java.hw06.shell.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is a parser for parsing a given file name expression.
 *
 * Its {@link #getNameBuilder()} method can be used to get a {@link NameBuilder} object
 * that will know how to construct the name described by the given expression.
 *
 * @author Bruna Dujmović
 *
 */
public class NameBuilderParser {

    /**
     * The {@link NameBuilderLexer} used for separating a given expression into tokens.
     */
    private NameBuilderLexer lexer;

    /**
     * The list of {@link NameBuilder} objects parsed from a given expression.
     */
    private List<NameBuilder> nameBuilders = new ArrayList<>();

    /**
     * Constructs a parser for a given file name expression.
     *
     * @param expression the expression that represents a file name
     * @throws NullPointerException if the given expression is {@code null}
     */
    public NameBuilderParser(String expression) {
        lexer = new NameBuilderLexer(Objects.requireNonNull(expression));

        while (lexer.hasNextToken()) {
            NameBuilderLexerToken token = lexer.nextToken();
            String value = token.getValue();

            if (token.getType() == NameBuilderLexerTokenType.SUBSTITUTION) {
                parseSubstitution(value);
            } else {
                nameBuilders.add(DefaultNameBuilders.text(value));
            }
        }
    }

    /**
     * Returns a composite {@link NameBuilder} that iterates over the {@link #nameBuilders}
     * list and executes each element.
     *
     * @return a composite {@link NameBuilder} that iterates over the {@link #nameBuilders}
     *         list and executes each element
     */
    public NameBuilder getNameBuilder() {
        return (result, sb) -> nameBuilders.forEach(
                builder -> builder.execute(result, sb)
        );
    }

    /**
     * Parses a given substitution string.
     *
     * @param value the substitution string
     * @throws IllegalArgumentException if the given substitution string is invalid
     * @throws NumberFormatException if a given substitution element can't be parsed
     *         to an integer
     */
    private void parseSubstitution(String value) {
        String[] parts = value.split(",");

        if (parts.length == 1) {
            int index = Integer.parseInt(parts[0].trim());

            nameBuilders.add(DefaultNameBuilders.group(index));

        } else if (parts.length == 2) {
            int index = Integer.parseInt(parts[0].trim());
            int minWidth = Integer.parseInt(parts[1].trim());
            char padding = parts[1].matches("0.") ? '0' : ' ';

            if (index < 0 || minWidth < 0) {
                throw new IllegalArgumentException(
                        "Substitution cannot contain negative integers!");
            }

            nameBuilders.add(DefaultNameBuilders.group(index, padding, minWidth));

        } else {
            throw new IllegalArgumentException(
                    "Invalid substitution \"" + value + "\".");
        }
    }
}
