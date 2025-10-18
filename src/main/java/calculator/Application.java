package calculator;

import calculator.controller.CalculatorController;
import calculator.service.CalculatorService;
import calculator.view.ConsoleInputView;
import calculator.view.ConsoleOutputView;

public class Application {
    public static void main(String[] args) {
        CalculatorController calculatorController = new CalculatorController(new ConsoleInputView(),
                new ConsoleOutputView(), new CalculatorService());
        calculatorController.run();
    }
}
