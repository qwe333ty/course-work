package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Admin;
import com.ak.work.client.entity.User;
import com.ak.work.client.entity.VisitHistory;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.producer.UserProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            System.out.println("\nМеню:");
            System.out.println("    1) Добавить пользователя");
            System.out.println("    2) Ограничить доступ пользователю");
            System.out.println("    3) Просмотреть историю посещений");
            System.out.println("    4) Выход");

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:
                    addUser();
                    break;
                case 2:
                    ban();
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
        System.out.println("\nВыберите роль добавляемого пользователя: ");
        System.out.println("    1) " + UserProducer.MANAGER_ROLE);
        System.out.println("    2) " + UserProducer.EXPERT_ROLE);

        Integer choice = InputUtils.userChoice(scanner);

        String role;

        if (choice == 1)
            role = UserProducer.MANAGER_ROLE;
        else
            role = UserProducer.EXPERT_ROLE;

        System.out.print("\nВведите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Введите почтовый адрес: ");
        String email = scanner.nextLine();

        String first;
        int second;


        if (choice == 1) {
            System.out.print("Введите название компании менеджера: ");
            first = scanner.nextLine();
            System.out.print("Введите опыт работы менеджера: ");
            second = scanner.nextInt();
        } else {
            System.out.print("Введите предыдущие проекты эксперта: ");
            first = scanner.nextLine();
            System.out.print("Введите опыт работы эксперта: ");
            second = scanner.nextInt();
        }

        User user = userProducer.regist(login, email, password, role, first, second);

        if (user != null){
            System.out.println();
            System.out.println("Пользователь успешно зарегистрирован.");
        }
        else
            System.out.println("Произошла ошибка при регистрации. Повторите попытку снова.");
    }

    private void getUserVisitHistory() {
        List<User> allUsers = userProducer.getAllUsers();
        System.out.println("\n          История посещений:");
        for (User user : allUsers) {
            System.out.format("User %s\n", user.getUsername());
            List<VisitHistory> userVisitHistory = userProducer.getUserVisitHistory(user.getId());
            for (VisitHistory visitHistory : userVisitHistory) {
                System.out.println(visitHistory.getId() + " - " + visitHistory.getVisitTime());
            }
            System.out.println("------------------------------------");
        }
    }

    private void ban() {
        List<User> allUsers = userProducer.getAllUsers();
        Map<String, User> a = allUsers.stream().collect(Collectors.toMap(User::getUsername, user -> user));
        String login = null;

        System.out.println("\nВведите логин пользователя, которого необходимо заблокировать: ");
        login = scanner.nextLine();

        User userWhereLoginIs = a.get(login);
        if (userWhereLoginIs != null) {
            userProducer.changeUserStatus(a.get(login).getId(),true);
        }
        else {
            System.out.println("\nПользователь с таким логином не найден. Проверьте данные введенные данные.");
        }
    }
}
