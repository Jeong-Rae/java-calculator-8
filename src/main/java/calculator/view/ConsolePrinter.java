package calculator.view;

public final class ConsolePrinter {
    private ConsolePrinter() {}

    public static void print(String message) {
        System.out.println(message);
    }

    public static void emptyLine() {
        System.out.println();
    }
}
