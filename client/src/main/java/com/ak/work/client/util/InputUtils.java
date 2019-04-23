package com.ak.work.client.util;

import java.util.Scanner;

public class InputUtils {
    public static Integer userChoice(Scanner scanner) {
        Integer number = null;
        do {
            System.out.println();
            System.out.print("Ваш выбор: ");
            String string = scanner.nextLine();
            try {
                number = Integer.valueOf(string);
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        } while (number == null);

        return number;
    }
}
