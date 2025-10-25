package calculator.model;

public final class Expression {
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
        if (!Header.existsIn(input)) {
            Delimiters delimiters = Delimiters.defaults();
            Numbers numbers = Numbers.parse(input, delimiters);
            return new Expression(delimiters, numbers);
        }

        Header header = Header.parse(input);
        Delimiters delimiters = Delimiters.fromHeader(header.delimiter());
        Numbers numbers = Numbers.parse(header.body(), delimiters);

        return new Expression(delimiters, numbers);
    }

    public Delimiters delimiters() {
        return this.delimiters;
    }

    public Numbers numbers() {
        return this.numbers;
    }
}
