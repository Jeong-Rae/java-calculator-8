package calculator.controller;

import calculator.service.CalculatorService;
import calculator.view.ConsoleInputView;
import calculator.view.ConsoleOutputView;

public class CalculatorController {

    private final ConsoleInputView inputView;
    private final ConsoleOutputView outputView;
    private final CalculatorService calculatorService;

    public CalculatorController(ConsoleInputView inputView, ConsoleOutputView outputView,
            CalculatorService calculatorService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.calculatorService = calculatorService;
    }

    public void run() {
        try {
            String input = inputView.readUserInput();
            int sum = calculatorService.calculate(input);
            outputView.printResult(sum);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            throw e;
        }
    }
}
