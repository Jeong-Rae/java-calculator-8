package calculator.model;

import java.util.List;
import java.util.Objects;

/**
 * Numbers는 {@link PositiveNumber} 목록을 불변으로 유지하고 합산 연산을 제공한다.
 * <p>
 * 필요성: 토큰 파싱 결과를 안전하게 보관하고, 합계 계산이 비즈니스 규칙을 준수하도록 단일 지점을 제공한다.
 * 참조: {@link Expression}이 {@link Delimiters}에서 분리된 토큰을 {@link PositiveNumber}로 변환할 때 사용된다.
 */
public record Numbers(List<PositiveNumber> values) {
    public Numbers {
        values = List.copyOf(values);
    }

    public int sum() {
        return values.stream().mapToInt(PositiveNumber::value).sum();
    }

    public static Numbers empty() {
        return new Numbers(List.of());
    }

    public static Numbers parse(String raw, Delimiters delimiters) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("본문 입력은 비어 있을 수 없습니다.");
        }
        Objects.requireNonNull(delimiters, "delimiters");

        List<PositiveNumber> positiveNumbers = delimiters.tokenize(raw)
                .filter(token -> !token.isEmpty())
                .map(PositiveNumber::parse)
                .toList();
        return new Numbers(positiveNumbers);
    }
}
