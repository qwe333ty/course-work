package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Manager;
import com.ak.work.client.entity.Problem;
import com.ak.work.client.entity.Solution;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ManagerMenu extends Menu {

    @Override
    public void start(User user, Scanner scanner) {
        super.manager = (Manager) user;
        super.expert = null;
        super.scanner = scanner;

        mainMenu();
    }

    private void mainMenu() {
        main:
        while (true) {
            System.out.println("    1) Посмотреть список всех проблем");
            System.out.println("    2) Добавить проблему");
            System.out.println("    3) Удалить проблему");
            System.out.println("    4) Получить экспертную оценку проблемы");
            System.out.println("    5) Выйти из учетной записи");

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:
                    showAllProblems();
                    break;
                case 2:
                    createProblem();
                    break;
                case 3:
                    deleteProblem();
                    break;
                case 4:
                    estimateProblem();
                    break;
                case 5:
                default:
                    break main;
            }

        }
    }

    private void createProblem() {
        System.out.println();

        Problem problem = new Problem();
        System.out.print("    Введите проблему: ");
        String header = scanner.nextLine();

        problem.setHeader(header);
        problem.setManager(manager);
        problem = problemProducer.save(problem);

        List<Solution> solutions = new ArrayList<>();
        solutions.add(createSolution(problem));

        String yn;
        do {
            System.out.print("    Хоте ли бы еще добавить решение? (да/нет)   ");
            yn = scanner.nextLine();

            if (yn.compareToIgnoreCase("да") == 0) {
                solutions.add(createSolution(problem));
            }
        } while (yn.compareToIgnoreCase("нет") != 0);
        solutionProducer.saveAll(solutions);

        System.out.println("Проблема и её решения успешно созданы.");
    }

    private Solution createSolution(Problem problem) {
        System.out.println();
        System.out.println("        Добавляем альтернативное решение.");
        System.out.print("        Введите решение: ");
        String header = scanner.nextLine();

        Solution solution = new Solution();
        solution.setProblem(problem);
        solution.setHeader(header);
        solution.setRating(0.0);
        return solution;
    }

    private void deleteProblem() {
        System.out.println();

        if (problemIdForTask == null || !problemProducer.checkIfExists(problemIdForTask)) {
            System.out.println("Проблема не была выбрана либо удалена.");
            return;
        }
        System.out.println("Будет удалена проблема, выбранная в пункте 1)");
        System.out.println("Начинаем удаление...");

        problemProducer.deleteProblem(problemIdForTask);

        System.out.println("Проблема была успешно удалена.");
    }

    private void estimateProblem() {

    }
}
