package calculator.model;

/**
 * PositiveNumber는 개별 숫자 토큰이 0 이상 정수임을 보장하고 파싱 예외를 도메인 수준으로 변환한다.
 * <p>
 * 필요성: {@link Numbers}가 음수나 비정상 문자열을 합계에 포함하지 않도록 사전에 방어한다.
 * 참조: {@link Numbers}가 토큰을 {@link PositiveNumber#parse(String)}로 변환할 때 사용된다.
 */
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
