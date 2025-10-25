package calculator.model;

/**
 * Expression은 원시 입력 문자열을 {@link Header}와 본문으로 분리하고 {@link Delimiters}와 {@link Numbers} 생성을 조정한다.
 * <p>
 * 필요성: {@link calculator.service.CalculatorService}가 파싱 세부 구현을 의존하지 않도록 입력 해석을 전담한다.
 * 참조: {@link Header}에서 구분자 정보를 추출하고, {@link Delimiters}와 {@link Numbers}를 결합해 합산 가능한 상태를 만든다.
 */
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
