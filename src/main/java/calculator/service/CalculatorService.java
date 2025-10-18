package calculator.service;

import calculator.model.Expression;

public class CalculatorService {
    public int calculate(String input) {
        Expression expression = Expression.from(input);
        return expression.numbers().sum();
    }
}
