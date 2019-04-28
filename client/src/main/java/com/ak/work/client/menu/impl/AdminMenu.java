package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Admin;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

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
            System.out.println("    1) ");
            System.out.println("    2) ");
            System.out.println("    3) ");
            System.out.println("    4) ");
            System.out.println("    5) ");
            // .......................

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:

                case 2:
                case 3:
                case 4:
                case 5:
                default:
                    break main;
            }

        }
    }
}
