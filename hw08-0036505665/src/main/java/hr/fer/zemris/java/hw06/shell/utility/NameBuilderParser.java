package hr.fer.zemris.java.hw06.shell.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NameBuilderParser {

    private NameBuilderLexer lexer;

    private List<NameBuilder> nameBuilders = new ArrayList<>();

    /**
     * Constructs a parser for
     *
     * @param expression
     * @throws NullPointerException
     */
    public NameBuilderParser(String expression) {
        lexer = new NameBuilderLexer(Objects.requireNonNull(expression));

        while (lexer.hasNextToken()) {
            NameBuilderLexerToken token = lexer.nextToken();
            String value = token.getValue();

            if (token.getType() == NameBuilderLexerTokenType.SUBSTITUTION) {
                parseSubstitution(value);
            } else {
                nameBuilders.add(Builders.text(value));
            }
        }
    }

    public NameBuilder getNameBuilder() {
        return (result, sb) ->
                nameBuilders.forEach((builder) -> builder.execute(result, sb));
    }

    /**
     * Parses a given substitution string.
     *
     * @param value the substitution string
     * @throws IllegalArgumentException if the given substitution string is invalid
     */
    private void parseSubstitution(String value) {
        String[] parts = value.split(",");

        if (parts.length == 1) {
            int index = Integer.parseInt(parts[0].trim());

            nameBuilders.add(Builders.group(index));

        } else if (parts.length == 2) {
            int index = Integer.parseInt(parts[0].trim());
            int minWidth = Integer.parseInt(parts[1].trim());
            char padding = parts[1].matches("0.") ? '0' : ' ';

            if (index < 0 || minWidth < 0) {
                throw new IllegalArgumentException(
                        "Substitution cannot contain negative integers!");
            }

            nameBuilders.add(Builders.group(index, padding, minWidth));

        } else {
            throw new IllegalArgumentException(
                    "Invalid substitution \"" + value + "\".");
        }
    }
}
