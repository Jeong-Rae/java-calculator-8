package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    // 성공



    @Test
    void 기본_쉼표_구분자를_처리한다() {
        assertSimpleTest(() -> {
            run("1,2,3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 기본_콜론_구분자를_처리한다() {
        assertSimpleTest(() -> {
            run("1:2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 기본_복합_구분자를_처리한다() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자가_단일문자인_경우() {
        assertSimpleTest(() -> {
            run("//;\\n1;2;3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자가_여러문자인_경우() {
        assertSimpleTest(() -> {
            run("//abc\\n1abc2abc3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자가_특수문자인_경우() {
        assertSimpleTest(() -> {
            run("//#\\n1#2#3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자가_역슬래시인_경우() {
        assertSimpleTest(() -> {
            run("//\\\\\\n1\\\\2\\\\3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 헤더는_있지만_비어있는_경우_기본구분자로처리() {
        assertSimpleTest(() -> {
            run("//\\n1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    // 실패 및 예외

    @Test
    void 빈문자열은_예외발생() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException(" "))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 음수가_포함되면_예외발생() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException("1,-2,3"))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 숫자가_아닌_값이_포함되면_예외발생() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException("1,a,3"))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 헤더형식이_잘못된경우_예외발생() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException("//;1;2;3"))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 헤더만있고_본문이_없는경우_예외발생() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException("//\\n"))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 공백_문자가_있는_입력은_무시된다() {
        assertSimpleTest(() -> {
            run(" 1 , 2 : 3 ");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void null_입력은_예외발생() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException((String) null))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 숫자는_커스텀_구분자로_사용할_수없다() {
        assertSimpleTest(() -> assertThatThrownBy(() -> runException("//1\\n1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자는 커스텀 구분자로 사용할 수 없습니다."));
    }

    @Override
    public void runMain() {
        Application.main(new String[] {});
    }
}
