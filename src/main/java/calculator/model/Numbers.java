package calculator.model;

import java.util.List;

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
}
