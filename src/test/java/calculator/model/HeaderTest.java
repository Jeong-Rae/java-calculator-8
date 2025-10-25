package calculator.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HeaderTest {

    @Test
    void 헤더가_존재하는지_확인한다() {
        assertThat(Header.existsIn("//abc\\n1abc2")).isTrue();
        assertThat(Header.existsIn("1,2,3")).isFalse();
    }

    @Test
    void 헤더를_파싱하면_구분자와_본문을_제공한다() {
        Header header = Header.parse("//abc\n1abc2abc3");

        assertThat(header.delimiter().value()).isEqualTo("abc");
        assertThat(header.body()).isEqualTo("1abc2abc3");
    }

    @Test
    void 헤더_노말라이징을_적용한다() {
        Header header = Header.parse("//abc\\n1abc2abc3");

        assertThat(header.value()).isEqualTo("//abc\n");
        assertThat(header.delimiter().value()).isEqualTo("abc");
    }

    @Test
    void 윈도우_개행도_노말라이징한다() {
        Header header = Header.parse("//abc\r\n1abc2abc3");

        assertThat(header.value()).isEqualTo("//abc\n");
        assertThat(header.body()).isEqualTo("1abc2abc3");
    }

    @Test
    void 줄바꿈이_없는_헤더는_예외를_발생시킨다() {
        assertThatThrownBy(() -> Header.parse("//;123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("커스텀 구분자 형식 오류입니다");
    }

    @Test
    void 특수문자_구분자는_허용되지_않는다() {
        assertThatThrownBy(() -> Header.parse("//;\n1;2;3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("구분자는 알파벳 문자만 사용할 수 있습니다.");
    }
}
