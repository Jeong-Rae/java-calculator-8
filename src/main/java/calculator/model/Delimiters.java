package calculator.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class Delimiters {
    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final Set<Delimiter> DEFAULTS = Set.of(
            Delimiter.internal(COMMA),
            Delimiter.internal(COLON)
    );

    private final Set<Delimiter> values;
    private final Pattern splitPattern;

    private Delimiters(Set<Delimiter> values) {
        this.values = Set.copyOf(values);
        this.splitPattern = Pattern.compile(buildRegex(this.values));
    }

    public static Delimiters defaults() {
        return new Delimiters(DEFAULTS);
    }

    public static Delimiters fromHeader(Delimiter delimiter) {
        Objects.requireNonNull(delimiter, "delimiter");
        return defaults().add(delimiter);
    }

    public static Delimiters of(Set<Delimiter> values) {
        if (values == null || values.isEmpty())
            return defaults();
        return new Delimiters(values);
    }

    public Delimiters add(Delimiter delimiter) {
        Objects.requireNonNull(delimiter);
        // Set 불변 유지
        Set<Delimiter> merged = new LinkedHashSet<>(values);
        merged.add(delimiter);
        return new Delimiters(merged);
    }

    public Stream<String> tokenize(String input) {
        if (input == null || input.isBlank())
            return Stream.empty();
        return Stream.of(splitPattern.split(input, -1));
    }

    public Set<Delimiter> values() {
        return values;
    }

    private static String buildRegex(Set<Delimiter> delimiters) {
        String alternation = delimiters.stream().map(Delimiter::value).map(Delimiters::escapeRegex)
                .reduce((a, b) -> a + "|" + b).orElse(",");
        return alternation;
    }

    /**
     * regax 예약 문자에 대한 허용을 위한 이스케이프 처리
     * 
     * @param str
     * @return
     */
    private static String escapeRegex(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if ("\\.^$|?*+()[]{}".indexOf(c) >= 0)
                sb.append('\\');
            sb.append(c);
        }
        return sb.toString();
    }

}
