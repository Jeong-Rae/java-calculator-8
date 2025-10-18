package calculator.model;

public record Body(String raw) {
    public Body {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("body입력은 비어있을 수 없습니다.");
        }
    }
}
