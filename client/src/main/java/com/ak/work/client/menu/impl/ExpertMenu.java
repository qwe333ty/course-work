package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Expert;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ExpertMenu extends Menu {

    @Override
    public void start(User user, Scanner scanner) {
        super.expert = (Expert) user;
        super.manager = null;
        super.scanner = scanner;


        super.childSolutionToTask = null;
        super.solutionToTask = null;
        super.problemIdForTask = null;

        mainMenu();
    }

    private void mainMenu() {
        main:
        while (true) {
            System.out.println("    1) Посмотреть список всех проблем");
            System.out.println("    2) Выйти из учетной записи");

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:
                    showAllProblems();
                    break;
                case 2:
                default:
                    break main;
            }

        }
    }


}
