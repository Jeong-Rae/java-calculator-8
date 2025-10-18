package calculator.model;

import java.util.Objects;

public record Body(String raw) {
    public Body {
        Objects.requireNonNull(raw, "body는 null일 수 없습니다.");
        if (raw.isBlank()) {
            throw new IllegalArgumentException("body는 빈 문자열일 수 없습니다.");
        }
    }
}
