package calculator.model;

import java.util.List;
import java.util.Objects;

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
            throw new IllegalArgumentException("body입력은 비어있을 수 없습니다.");
        }
        Objects.requireNonNull(delimiters, "delimiters");

        List<PositiveNumber> positiveNumbers = delimiters.tokenize(raw)
                .filter(token -> !token.isEmpty())
                .map(PositiveNumber::parse)
                .toList();
        return new Numbers(positiveNumbers);
    }
}
