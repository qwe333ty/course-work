package com.ak.work.client.service;

import com.ak.work.client.producer.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MenuService {

    @Autowired
    private UserProducer userProducer;

    public int startMenu(Scanner scanner) {
        System.out.println("\n\n");
        System.out.println("Menu:");
        System.out.println("    1) Log in");
        System.out.println("    2) Exit");
        System.out.println("\n");

        do {
            System.out.println();
            System.out.print("Your choice: ");
            String number = scanner.nextLine();
            try {
                return Integer.valueOf(number);
            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER INTEGER NUMBER!");
            }
        } while (true);

    }

    public Boolean login(Scanner scanner) {
        System.out.print("login: ");
        String login = scanner.nextLine();

        System.out.print("password: ");
        String password = scanner.nextLine();

        System.out.println();
        return userProducer.checkIfAuthenticated(login, password);
    }
}
