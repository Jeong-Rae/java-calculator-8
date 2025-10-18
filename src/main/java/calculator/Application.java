package calculator;

import calculator.controller.CalculatorController;
import calculator.view.ConsoleInputView;
import calculator.view.ConsoleOutputView;

public class Application {
    public static void main(String[] args) {
        CalculatorController calculatorController =
                new CalculatorController(new ConsoleInputView(), new ConsoleOutputView());
        calculatorController.run();
    }
}
