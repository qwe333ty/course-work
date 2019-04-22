package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Manager;
import com.ak.work.client.entity.Problem;
import com.ak.work.client.entity.Solution;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.producer.ProblemProducer;
import com.ak.work.client.producer.SolutionProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ManagerMenu implements Menu {

    @Autowired
    private ProblemProducer problemProducer;

    @Autowired
    private SolutionProducer solutionProducer;

    private Manager manager;

    private Scanner scanner;

    private Integer problemIdForTask;

    @Override
    public void start(User user, Scanner scanner) {
        manager = (Manager) user;
        this.scanner = scanner;

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

    private void showAllProblems() {
        System.out.println();
        System.out.println("    Проблемы:");
        List<Problem> problems = problemProducer.findProblems(manager.getId());

        int counter = 0;
        for (; counter < problems.size(); counter++) {

            System.out.format("        %d) %s\n", (counter + 1), problems.get(counter).getHeader());
        }
        System.out.format("        %d) Выйти", ++counter);

        System.out.println();
        Integer choice = InputUtils.userChoice(scanner);
        if (choice == counter) {
            return;
        }

        Problem problem = problems.get(--choice);
        problemIdForTask = problem.getId();
        System.out.format("        Вы выбрали проблему: %d) %s", (choice + 1), problem.getHeader());

        showPossibleProblemSolutions(problemIdForTask);
    }

    private void showPossibleProblemSolutions(Integer problemId) {
        System.out.println();
        System.out.println("        Eё альтернативные решения:");

        List<Solution> solutions = solutionProducer.findSolutions(null, problemId);

        int counter = 0;
        for (; counter < solutions.size(); counter++) {

            System.out.format("            %d) %s\n", (counter + 1), solutions.get(counter).getHeader());
        }
        System.out.format("            %d) Выйти", ++counter);

        System.out.println();
        Integer choice = InputUtils.userChoice(scanner);
        if (choice == counter) {
            return;
        }

        Solution solution = solutions.get(--choice);
        System.out.format("        Вы выбрали решение: %d) %s", (choice + 1), solution.getHeader());
        System.out.println();
        System.out.println();
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

        //TODO: add check with 'exists' method
        if (problemIdForTask == null) {
            System.out.println("Перед удалением какой-либо проблемы нужно ее выбрать.");
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
