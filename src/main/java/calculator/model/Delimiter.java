package calculator.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Delimiter는 단일 구분자 값을 검증해 {@link Delimiters}가 신뢰할 수 있는 토큰 분리 기준을 유지하도록 한다.
 * <p>
 * 필요성: 커스텀 구분자가 허용 범위를 넘어서는 것을 사전에 차단해 정규식 주입이나 파싱 오류를 예방한다.
 * 참조: {@link Header}가 커스텀 구분자를 생성하고, {@link Delimiters}가 기본 구분자와 병합할 때 사용된다.
 */
public record Delimiter(String value) {
    private static final int MAX_LENGTH = 3;
    private static final Pattern ALLOWED = Pattern.compile("^[A-Za-z]+$");

    public static Delimiter custom(String value) {
        validate(value);
        return new Delimiter(value);
    }

    static Delimiter internal(String value) {
        Objects.requireNonNull(value, "delimiter");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("빈 구분자는 허용되지 않습니다.");
        }
        return new Delimiter(value);
    }

    private static void validate(String value) {
        Objects.requireNonNull(value, "delimiter");
        if (value.isBlank()) {
            throw new IllegalArgumentException("구분자는 비어 있을 수 없습니다.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("구분자는 최대 " + MAX_LENGTH + "글자까지 허용됩니다.");
        }
        if (!ALLOWED.matcher(value).matches()) {
            throw new IllegalArgumentException("구분자는 알파벳 문자만 사용할 수 있습니다.");
        }
    }

    public Delimiter {
        Objects.requireNonNull(value, "delimiter");
    }
}
