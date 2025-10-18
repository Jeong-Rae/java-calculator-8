package calculator.controller;

import calculator.model.Expression;
import calculator.view.ConsoleInputView;
import calculator.view.ConsoleOutputView;

public class CalculatorController {

    private final ConsoleInputView inputView;
    private final ConsoleOutputView outputView;

    public CalculatorController(ConsoleInputView inputView, ConsoleOutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        try {
            String input = inputView.readUserInput();
            Expression expression = Expression.from(input);
            int sum = expression.numbers().sum();
            outputView.printResult(sum);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            throw e;
        }
    }
}
