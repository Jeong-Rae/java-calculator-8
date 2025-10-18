package calculator.model;

public record Header(String raw) {
    public Header {
        if (raw == null) {
            throw new IllegalArgumentException("header는 null일 수 없습니다.");
        }
    }
}
