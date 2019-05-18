package com.ak.work.client.service;

import com.ak.work.client.producer.UserProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MenuService {

    @Autowired
    private UserProducer userProducer;

    public int startMenu(Scanner scanner) {
        System.out.println("\nМеню:");
        System.out.println("    1) Войти в систему");
        System.out.println("    2) Выход");

        return InputUtils.userChoice(scanner);
    }

    public Boolean login(Scanner scanner) {
        System.out.print("Логин: ");
        String login = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        System.out.println();
        return userProducer.checkIfAuthenticated(login, password, scanner);
    }
}
