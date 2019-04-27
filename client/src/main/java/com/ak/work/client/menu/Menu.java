package com.ak.work.client.menu;

import com.ak.work.client.entity.*;
import com.ak.work.client.producer.ProblemProducer;
import com.ak.work.client.producer.SolutionProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    @Autowired
    protected ProblemProducer problemProducer;

    @Autowired
    protected SolutionProducer solutionProducer;

    protected Manager manager;

    protected Expert expert;

    protected Scanner scanner;

    protected Integer problemIdForTask;

    protected Integer solutionOrderToTask;

    protected Integer childSolutionOrderToTask;

    protected List<Solution> solutions;

    public abstract void start(User user, Scanner scanner);

    protected void showAllProblems(boolean withInput, boolean resolved, boolean all, Boolean isRow, Integer row, Boolean inverse) {
        showProblemsWithoutSolutions(resolved);

        System.out.println();
        System.out.println("        Eё альтернативные решения:");

        this.solutions = solutionProducer.findSolutions(expert != null ? expert.getId() : null, problemIdForTask, all, isRow, row, inverse);
        if (this.solutions.isEmpty()) {
            List<Solution> temp = solutionProducer.findSolutions(
                    expert.getId(), problemIdForTask, true, false,
                    null, false);
            for (Solution solution : temp) {
                List<Solution> q = solutionProducer.findSolutions(
                        expert.getId(), problemIdForTask, false, true,
                        solution.getOrder(), false);
                if (!q.isEmpty()) {
                    solutionOrderToTask = q.get(0).getOrder();
                    this.solutions = q;
                    List<Solution> filteredSolutions = solutionProducer.findSolutions(
                            expert.getId(), problemIdForTask, false, true, solutionOrderToTask, false);
                    if (filteredSolutions.isEmpty()) {
                        solutionOrderToTask = null;
                        System.out.println("Нету доступных решений для оценки");
                    }
                    break;
                }
            }
        }
        showPossibleProblemSolutions(solutions, false, withInput);
    }

    protected void showProblemsWithoutSolutions(boolean resolved) {
        System.out.println();
        System.out.println("    Проблемы:");

        List<Problem> problems;
        if (manager == null) {
            problems = problemProducer.findProblems(null, resolved);
        } else {
            problems = problemProducer.findProblems(manager.getId(), resolved);
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
    }

    protected void showPossibleProblemSolutions(List<Solution> solutions, boolean isSubMenu, boolean withInput) {

        int counter = 0;
        for (; counter < solutions.size(); counter++) {

            System.out.format("            %d) %s\n", (counter + 1), solutions.get(counter).getHeader());
        }

        if (withInput) {
            System.out.format("            %d) Выйти", ++counter);

            System.out.println();
            Integer choice = InputUtils.userChoice(scanner);
            if (choice == counter) {
                return;
            }

            Solution solution = solutions.get(--choice);
            if (isSubMenu) {
                childSolutionOrderToTask = solution.getOrder();
            } else {
                solutionOrderToTask = solution.getOrder();
            }
            System.out.format("        Вы выбрали решение: %d) %s", (choice + 1), solution.getHeader());
        }

        System.out.println();
        System.out.println();
    }

}
