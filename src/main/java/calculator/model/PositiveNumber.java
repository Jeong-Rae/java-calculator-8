package calculator.model;

public record PositiveNumber(int value) {
    public PositiveNumber {
        if (value < 0) {
            throw new IllegalArgumentException(String.format("음수는 허용되지 않습니다: %d", value));
        }
    }

    public static PositiveNumber parse(String raw) {
        if (raw == null || raw.isBlank())
            throw new IllegalArgumentException("빈 숫자 토큰은 허용되지 않습니다.");
        try {
            int v = Integer.parseInt(raw.trim());
            return new PositiveNumber(v);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("정수가 아닙니다: %s", raw), e);
        }
    }
}
