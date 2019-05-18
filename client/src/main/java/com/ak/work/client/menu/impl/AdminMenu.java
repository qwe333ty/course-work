package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Admin;
import com.ak.work.client.entity.User;
import com.ak.work.client.entity.VisitHistory;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.producer.UserProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AdminMenu extends Menu {
    @Override
    public void start(User user, Scanner scanner) {
        super.admin = (Admin) user;
        super.expert = null;
        super.manager = null;
        super.scanner = scanner;


        super.childSolutionOrderToTask = null;
        super.solutionOrderToTask = null;
        super.problemIdForTask = null;
        super.solutions = null;

        mainMenu();
    }

    private void mainMenu() {
        main:
        while (true) {
            System.out.println("    1) Добавить пользователя");
            System.out.println("    2) Ограничить доступ пользователю");
            System.out.println("    3) Просмотреть историю посещений");
            System.out.println("    4) Выход");

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:
                    addUser();
                    break;
                case 2: //TODO
                    break;
                case 3:
                    getUserVisitHistory();
                    break;
                case 4:
                default:
                    break main;
            }

        }
    }

    private void addUser() {
        System.out.println("Выберите роль добавляемого пользователя: ");
        System.out.println("    1) " + UserProducer.MANAGER_ROLE);
        System.out.println("    2) " + UserProducer.EXPERT_ROLE);

        Integer choice = InputUtils.userChoice(scanner);

        String role;

        if (choice == 1)
            role = UserProducer.MANAGER_ROLE;
        else
            role = UserProducer.EXPERT_ROLE;

        System.out.println("Введите логин: ");
        String login = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.println("Введите почтовый адрес: ");
        String email = scanner.nextLine();

        String first;
        int second;

        if (choice == 1) {
            System.out.println("Введите название компании менеджера: ");
            first = scanner.nextLine();
            System.out.println("Введите опыт работы менеджера: ");
            second = scanner.nextInt();
        } else {
            System.out.println("Введите предыдущие проекты эксперта: ");
            first = scanner.nextLine();
            System.out.println("Введите опыт работы эксперта: ");
            second = scanner.nextInt();
        }


        User user = userProducer.regist(login, email, password, role, first, second);

        if (user != null)
            System.out.println("Пользователь успешно зарегистрирован.");
        else
            System.out.println("Произошла ошибка при регистрации. Повторите попытку снова.");
    }

    private void getUserVisitHistory() {
        List<User> allUsers = userProducer.getAllUsers();
        for (User user : allUsers) {
            System.out.format("User %s\n", user.getUsername());
            List<VisitHistory> userVisitHistory = userProducer.getUserVisitHistory(user.getId());
            for (VisitHistory visitHistory : userVisitHistory) {
                System.out.println(visitHistory.getId() + " - " + visitHistory.getVisitTime());
            }
            System.out.println("--------------------------------");
        }
    }
}
