package calculator.view;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInputView {

    public String readUserInput() {
        ConsolePrinter.print("덧셈할 문자열을 입력해 주세요.");
        return Console.readLine();
    };

}
