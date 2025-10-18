package calculator.model;

import java.util.List;

public final class Expression {
    private static final String HEADER_PREFIX = "//";
    private static final String HEADER_SUFFIX = "\n";
    private static final Header EMPTY_HEADER = new Header("");


    private final Header header;
    private final Body body;

    private Expression(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public static Expression from(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력은 비어 있을 수 없습니다.");
        }
        if (!hasHeader(input)) {
            return new Expression(EMPTY_HEADER, new Body(input));
        }

        input = normalizeHeaderNewline(input);
        final int nl = newlineIndexOrThrow(input);
        final String headerRaw = input.substring(HEADER_PREFIX.length(), nl);
        final String bodyRaw = input.substring(nl + HEADER_SUFFIX.length());

        return new Expression(new Header(headerRaw), new Body(bodyRaw));
    }

    private static String normalizeHeaderNewline(String input) {
        return input.replaceFirst("^//([^\\n]*)\\\\n", "//$1\n");
    }

    public Header header() {
        return this.header;
    }

    public Body body() {
        return this.body;
    }

    public Delimiters delimiters() {
        final String raw = this.header.raw();
        if (raw == null || raw.isBlank()) {
            return Delimiters.defaults();
        }
        return Delimiters.defaults().add(raw);
    }

    public Numbers numbers() {
        final String raw = this.body.raw();
        if (raw.isBlank()) {
            return Numbers.empty();
        }
        List<PositiveNumber> positiveNumbers = this.delimiters().tokenize(raw)
                .filter(s -> !s.isEmpty()).map(PositiveNumber::parse).toList();
        return new Numbers(positiveNumbers);
    }

    private static boolean hasHeader(String input) {
        return input.startsWith(HEADER_PREFIX);
    }

    private static int newlineIndexOrThrow(String input) {
        int idx = input.indexOf(HEADER_SUFFIX);
        if (idx < 0) {
            throw new IllegalArgumentException(
                    "커스텀 구분자 형식 오류입니다. `//[구분자]\\n[본문]` 형태로 입력하세요. 예: `//;\\\\n1;2;3`");
        }
        return idx;
    }
}
