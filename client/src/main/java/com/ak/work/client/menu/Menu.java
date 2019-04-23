package com.ak.work.client.menu;

import com.ak.work.client.entity.*;
import com.ak.work.client.producer.ProblemProducer;
import com.ak.work.client.producer.SolutionProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class Menu {

    @Autowired
    protected ProblemProducer problemProducer;

    @Autowired
    protected SolutionProducer solutionProducer;

    protected Manager manager;

    protected Expert expert;

    protected Scanner scanner;

    protected Integer problemIdForTask;

    protected Integer solutionToTask;

    protected Integer childSolutionToTask;

    public abstract void start(User user, Scanner scanner);

    protected void showAllProblems() {
        System.out.println();
        System.out.println("    Проблемы:");

        List<Problem> problems;
        if (manager == null) {
            problems = problemProducer.findProblems(null);
        } else {
            problems = problemProducer.findProblems(manager.getId());
        }

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

        System.out.println();
        System.out.println("        Eё альтернативные решения:");

        List<Solution> solutions = solutionProducer.findSolutions(null, problemIdForTask);
        showPossibleProblemSolutions(solutions, false);

        if (expert != null) {
            alternativeSolutionsMenu(solutions);
        }
    }

    private void showPossibleProblemSolutions(List<Solution> solutions, boolean isSubMenu) {

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
        if (isSubMenu) {
            childSolutionToTask = solution.getId();
        } else {
            solutionToTask = solution.getId();
        }
        System.out.format("        Вы выбрали решение: %d) %s", (choice + 1), solution.getHeader());

        System.out.println();
        System.out.println();
    }

    private void alternativeSolutionsMenu(List<Solution> solutions) {
        System.out.println();
        System.out.println("            1) Дать оценку альтернативе");
        System.out.println("            2) Редактировать альтернативу");
        System.out.println("            3) Удалить оценку альтернативе");
        System.out.println("            4) Выход");

        Integer choice = InputUtils.userChoice(scanner);
        switch (choice) {
            case 1:
                subAlternativeMenu(solutions);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
            default:
                break;
        }
    }

    private void subAlternativeMenu(List<Solution> solutions) {
        System.out.println();
        System.out.println("            Доступные решения для сравнений:");

        List<Solution> filteredSolutions = solutions.stream()
                .filter(e -> e.getId() != solutionToTask)
                .collect(Collectors.toList());
        showPossibleProblemSolutions(filteredSolutions, true);

        System.out.println("-----------------------" + solutionToTask + " and " + childSolutionToTask);
    }


}
