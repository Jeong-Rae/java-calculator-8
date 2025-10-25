package calculator.model;

import java.util.Objects;

import calculator.model.Delimiter;

public final class Header {
    private static final String PREFIX = "//";
    private static final String HEADER_SUFFIX = "\n";
    private static final String HEADER_FORMAT_ERROR =
            "커스텀 구분자 형식 오류입니다. `//[구분자]\\n[본문]` 형태로 입력하세요. 예: `//;\\n1;2;3`";

    private final String value;
    private final String body;
    private final Delimiter delimiter;

    private Header(String value, Delimiter delimiter, String body) {
        this.value = value;
        this.delimiter = delimiter;
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

        Delimiter delimiter = parseDelimiter(normalizedHeader);
        return new Header(normalizedHeader, delimiter, bodyPart);
    }

    private static Delimiter parseDelimiter(String normalizedHeader) {
        String rawDelimiter = normalizedHeader.substring(PREFIX.length(),
                normalizedHeader.length() - HEADER_SUFFIX.length());
        if (rawDelimiter.isBlank()) {
            throw new IllegalArgumentException("구분자는 비어 있을 수 없습니다.");
        }
        return Delimiter.custom(rawDelimiter);
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

    public Delimiter delimiter() {
        return delimiter;
    }

    public String body() {
        return body;
    }
}
