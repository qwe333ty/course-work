package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Expert;
import com.ak.work.client.entity.Solution;
import com.ak.work.client.entity.SolutionHistory;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ExpertMenu extends Menu {

    @Override
    public void start(User user, Scanner scanner) {
        super.expert = (Expert) user;
        super.manager = null;
        super.admin = null;
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
            System.out.println("    1) Просмотреть информацию о компании");
            System.out.println("    2) Просмотреть проблемы");
//            System.out.println("    3) Просмотреть решенные проблемы");
            System.out.println("    3) Изменить оценку");
            System.out.println("    4) Выход");

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:
                    showCompanyInfo();
                    break;
                case 2:
                    showAllProblems(true, false, false, false, null, false);
                    alternativeSolutionsMenu();
                    break;
//                case 3:
//                    showAllProblems(false, true, true, null, null, false);
//                    break;
                case 3:
                    showProblemsWithoutSolutions(false);
                    updateSolutionHistory();
                    break;
                case 4:
                default:
                    break main;
            }

        }
    }

    private void showCompanyInfo() {
        System.out.println("МЯВК");
    }

    private void alternativeSolutionsMenu() {
        if (solutionOrderToTask == null) {
            return;
        }

        System.out.println();
        System.out.println("        1) Дать оценку решению проблемы");
        System.out.println("        2) Выход");

        Integer choice = InputUtils.userChoice(scanner);
        switch (choice) {
            case 1:
                subAlternativeMenu();
                break;
            case 2:
            default:
                break;
        }
    }

    private void subAlternativeMenu() {
        System.out.println();
        System.out.println("        Доступные решения для сравнений:");

        List<Solution> filteredSolutions = solutionProducer.findSolutions(
                expert.getId(), problemIdForTask, false, true, solutionOrderToTask, false);
        solutionChoice(filteredSolutions);

        setValuesToMatrix();
    }

    /**
     * сохраняем значения 0 и 1 в матрице по-одиночке!!!
     */
    private void setValuesToMatrix() {
        SolutionHistory solutionHistory = new SolutionHistory();
        solutionHistory.setProblem(problemIdForTask);
        solutionHistory.setUser(expert.getId());
        solutionHistory.setRow(solutionOrderToTask);
        solutionHistory.setColumn(childSolutionOrderToTask);
        solutionHistory.setValue(1);

        solutionProducer.saveSolutionHistory(solutionHistory);

        solutionHistory.setProblem(problemIdForTask);
        solutionHistory.setUser(expert.getId());
        solutionHistory.setRow(childSolutionOrderToTask);
        solutionHistory.setColumn(solutionOrderToTask);
        solutionHistory.setValue(0);

        solutionProducer.saveSolutionHistory(solutionHistory);
    }

    private void updateSolutionHistory() {
        System.out.println();
        showPossibleProblemSolutions(solutionProducer.findSolutions(
                expert.getId(), problemIdForTask, false, false,
                null, true), false, true);

        System.out.println();
        System.out.println("            Доступные решения для изменения:");

        List<Solution> filteredSolutions = solutionProducer.findSolutions(
                expert.getId(), problemIdForTask, false, true,
                solutionOrderToTask, true);

        solutionChoice(filteredSolutions);

        updateValuesInMatrix();
    }

    private void solutionChoice(List<Solution> filteredSolutions) {
        if (filteredSolutions.isEmpty()) {
            System.out.println("        Нет доступных альтернативных решений.");
            return;
        }

        showPossibleProblemSolutions(filteredSolutions, true, true);

        //FIXME: придумать что лучше показывать пользователю при выборе
        // какое решение лучше, относительно другого + добавить проверку на ввод чисел
        System.out.println("\n" + (solutionOrderToTask + 1) + " or " + (childSolutionOrderToTask + 1) + "?");
        int choice = InputUtils.userChoice(scanner);
        choice--;

        if (choice != solutionOrderToTask) {
            childSolutionOrderToTask = solutionOrderToTask;
            solutionOrderToTask = choice;
        }
    }

    /**
     * обновляем значения 0 и 1 в матрице по-одиночке!!!
     */
    private void updateValuesInMatrix() {
        SolutionHistory solutionHistory = new SolutionHistory();
        solutionHistory.setProblem(problemIdForTask);
        solutionHistory.setUser(expert.getId());
        solutionHistory.setRow(solutionOrderToTask);
        solutionHistory.setColumn(childSolutionOrderToTask);
        solutionHistory.setValue(1);

        solutionProducer.updateSolutionHistory(solutionHistory, solutionOrderToTask, childSolutionOrderToTask);

        solutionHistory.setProblem(problemIdForTask);
        solutionHistory.setUser(expert.getId());
        solutionHistory.setRow(childSolutionOrderToTask);
        solutionHistory.setColumn(solutionOrderToTask);
        solutionHistory.setValue(0);

        solutionProducer.updateSolutionHistory(solutionHistory, childSolutionOrderToTask, solutionOrderToTask);
    }
}
