package hr.fer.zemris.java.hw06.shell.utility;

public class Builders {

    static NameBuilder text(String text) {
        return (result, sb) -> sb.append(text);
    }

    static NameBuilder group(int index) {
        return (result, sb) -> sb.append(result.group(index));
    }

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
