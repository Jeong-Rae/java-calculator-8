package calculator.model;

import java.util.Objects;

public final class Header {
    private static final String PREFIX = "//";
    private static final String HEADER_SUFFIX = "\n";
    private static final String HEADER_FORMAT_ERROR =
            "커스텀 구분자 형식 오류입니다. `//[구분자]\\n[본문]` 형태로 입력하세요. 예: `//;\\n1;2;3`";

    private final String value;
    private final String body;

    private Header(String value, String body) {
        this.value = value;
        this.body = body;
    }

    public static boolean existsIn(String input) {
        return input != null && input.startsWith(PREFIX);
    }

    public static Header parse(String input) {
        Objects.requireNonNull(input, "입력은 null일 수 없습니다.");

        String normalized = normalize(input);
        if (!existsIn(normalized)) {
            throw new IllegalArgumentException(HEADER_FORMAT_ERROR);
        }

        int newlineIndex = normalized.indexOf(HEADER_SUFFIX);
        if (newlineIndex < 0) {
            throw new IllegalArgumentException(HEADER_FORMAT_ERROR);
        }

        String headerPart = normalized.substring(0, newlineIndex + HEADER_SUFFIX.length());
        String bodyPart = normalized.substring(newlineIndex + HEADER_SUFFIX.length());
        String normalizedHeader = normalizeValue(headerPart);

        return new Header(normalizedHeader, bodyPart);
    }

    private static String normalize(String input) {
        String withoutCarriageReturn = input.replace("\r\n", "\n").replace("\r", "\n");
        return withoutCarriageReturn.replaceFirst("^//([^\\n]*)\\\\n", "//$1\n");
    }

    private static String normalizeValue(String headerPart) {
        String normalized = headerPart.replace("\r\n", "\n").replace("\r", "\n");
        if (!normalized.endsWith(HEADER_SUFFIX)) {
            normalized = normalized + HEADER_SUFFIX;
        }
        return normalized;
    }

    public String value() {
        return value;
    }

    public String delimiter() {
        return value.substring(PREFIX.length(), value.length() - HEADER_SUFFIX.length());
    }

    public String body() {
        return body;
    }
}
