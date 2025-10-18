package calculator.view;

public class ConsoleOutputView {
    public void printResult(int result) {
        ConsolePrinter.print(String.format("결과 : %d", result));
    }


    public void printError(String message) {
        ConsolePrinter.print(message);
    }
}
