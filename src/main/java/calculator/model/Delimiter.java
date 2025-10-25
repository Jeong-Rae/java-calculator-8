package calculator.model;

import java.util.Objects;
import java.util.regex.Pattern;

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
