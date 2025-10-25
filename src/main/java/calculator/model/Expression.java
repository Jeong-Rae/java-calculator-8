package calculator.model;

public final class Expression {
    private static final String HEADER_PREFIX = "//";
    private static final String HEADER_SUFFIX = "\n";

    private final Delimiters delimiters;
    private final Numbers numbers;

    private Expression(Delimiters delimiters, Numbers numbers) {
        this.delimiters = delimiters;
        this.numbers = numbers;
    }

    public static Expression from(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력은 비어 있을 수 없습니다.");
        }
        if (!hasHeader(input)) {
            Delimiters delimiters = Delimiters.defaults();
            Numbers numbers = Numbers.parse(input, delimiters);
            return new Expression(delimiters, numbers);
        }

        input = normalizeHeaderNewline(input);
        final int nl = newlineIndexOrThrow(input);
        final String headerRaw = input.substring(HEADER_PREFIX.length(), nl);
        final String bodyRaw = input.substring(nl + HEADER_SUFFIX.length());

        Delimiters delimiters = Delimiters.fromHeader(headerRaw);
        Numbers numbers = Numbers.parse(bodyRaw, delimiters);

        return new Expression(delimiters, numbers);
    }

    private static String normalizeHeaderNewline(String input) {
        return input.replaceFirst("^//([^\\n]*)\\\\n", "//$1\n");
    }

    public Delimiters delimiters() {
        return this.delimiters;
    }

    public Numbers numbers() {
        return this.numbers;
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

