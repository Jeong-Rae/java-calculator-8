package calculator.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Delimiters는 기본 구분자와 {@link Delimiter} 값 객체를 병합해 정규식 토큰 분리 패턴을 제공한다.
 * <p>
 * 필요성: 입력 문자열 분리에 필요한 정책을 한곳에서 관리해 {@link Numbers}가 안전하게 토큰 스트림을 얻도록 보장한다.
 * 참조: {@link Expression}이 {@link Header}에서 추출한 커스텀 {@link Delimiter}를 추가할 때 사용된다.
 */
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
     * regex 예약 문자를 이스케이프해 정규식 패턴을 안전하게 구성한다.
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
