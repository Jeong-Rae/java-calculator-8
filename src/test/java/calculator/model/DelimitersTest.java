package calculator.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DelimitersTest {

    @Test
    void 커스텀_구분자를_생성하면_알파벳만_허용한다() {
        Delimiter delimiter = Delimiter.custom("abc");

        assertThat(delimiter.value()).isEqualTo("abc");
    }

    @Test
    void 숫자를_포함한_구분자는_예외() {
        assertThatThrownBy(() -> Delimiter.custom("a1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("알파벳 문자만");
    }

    @Test
    void 특수문자를_포함한_구분자는_예외() {
        assertThatThrownBy(() -> Delimiter.custom("a!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("알파벳 문자만");
    }

    @Test
    void 최대_길이를_초과하면_예외() {
        assertThatThrownBy(() -> Delimiter.custom("abcd"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대 3글자");
    }

    @Test
    void 구분자_집합에_커스텀_구분자를_추가할_수_있다() {
        Delimiters delimiters = Delimiters.fromHeader(Delimiter.custom("ab"));

        assertThat(delimiters.values()).contains(Delimiter.custom("ab"));
        assertThat(delimiters.tokenize("1ab2").toList()).containsExactly("1", "2");
    }

    @Test
    void 집합_팩토리에서_null_또는_빈값은_기본값으로_대체한다() {
        assertThat(Delimiters.of(null).values()).isNotEmpty();
        assertThat(Delimiters.of(Set.of()).values()).isNotEmpty();
    }
}
