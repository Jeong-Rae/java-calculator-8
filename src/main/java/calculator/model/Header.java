package calculator.model;

public record Header(String raw) {
    public Header {
        if (raw == null) {
            throw new IllegalArgumentException("header는 null일 수 없습니다.");
        }
        if (!raw.isBlank() && raw.matches("\\d+")) {
            throw new IllegalArgumentException("숫자는 커스텀 구분자로 사용할 수 없습니다.");
        }
    }
}
