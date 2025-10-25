package calculator.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HeaderTest {

    @Test
    void 헤더가_존재하는지_확인한다() {
        assertThat(Header.existsIn("//;\\n1;2")).isTrue();
        assertThat(Header.existsIn("1,2,3")).isFalse();
    }

    @Test
    void 헤더를_파싱하면_구분자와_본문을_제공한다() {
        Header header = Header.parse("//;\n1;2;3");

        assertThat(header.delimiter()).isEqualTo(";");
        assertThat(header.body()).isEqualTo("1;2;3");
    }

    @Test
    void 헤더_노말라이징을_적용한다() {
        Header header = Header.parse("//;\\n1;2;3");

        assertThat(header.value()).isEqualTo("//;\n");
        assertThat(header.delimiter()).isEqualTo(";");
    }

    @Test
    void 윈도우_개행도_노말라이징한다() {
        Header header = Header.parse("//;\r\n1;2;3");

        assertThat(header.value()).isEqualTo("//;\n");
        assertThat(header.body()).isEqualTo("1;2;3");
    }

    @Test
    void 줄바꿈이_없는_헤더는_예외를_발생시킨다() {
        assertThatThrownBy(() -> Header.parse("//;123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("커스텀 구분자 형식 오류입니다");
    }
}
