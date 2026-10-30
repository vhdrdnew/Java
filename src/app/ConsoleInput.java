package app;
import java.util.Scanner;

public class ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Помилка: потрібно ввести ціле число (наприклад, 3).");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Помилка: потрібно ввести число (наприклад, 2.5).");
            }
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            String answer = readLine(prompt).toLowerCase();
            if (answer.equals("y") || answer.equals("yes") || answer.equals("так")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no") || answer.equals("ні")) {
                return false;
            }
            System.out.println("Введіть y (так) або n (ні).");
        }
    }

    public void close() {
        scanner.close();
    }
}