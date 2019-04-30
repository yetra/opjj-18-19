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
                String[] valueParts = value.split(",");

                if (valueParts.length == 1) {
                    int index = Integer.parseInt(valueParts[0].trim());
                    nameBuilders.add(Builders.group(index));

                } else if (valueParts.length == 2) {
                    int index = Integer.parseInt(valueParts[0].trim());
                    int minWidth = Integer.parseInt(valueParts[1].trim());
                    char padding = ' ';

                    if (valueParts[1].matches("0.")) {
                        minWidth = Integer.parseInt(valueParts[1].trim().substring(1));
                        padding = '0';
                    }

                    nameBuilders.add(Builders.group(index, padding, minWidth));

                } else {
                    throw new IllegalArgumentException("Invalid substitution \"" + value + "\".");
                }
            } else {
                nameBuilders.add(Builders.text(value));
            }
        }
    }

    public NameBuilder getNameBuilder() {
        return (result, sb) ->
                nameBuilders.forEach((builder) -> builder.execute(result, sb));
    }
}
