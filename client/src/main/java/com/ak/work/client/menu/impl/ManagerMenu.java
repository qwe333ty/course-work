package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Manager;
import com.ak.work.client.entity.Problem;
import com.ak.work.client.entity.Solution;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import com.ak.work.client.util.InputUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ManagerMenu extends Menu {

    @Override
    public void start(User user, Scanner scanner) {
        super.manager = (Manager) user;
        super.expert = null;
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
            System.out.println("    1) Посмотреть список всех проблем");
            System.out.println("    2) Добавить проблему");
            System.out.println("    3) Удалить проблему");
            System.out.println("    4) Получить экспертную оценку проблемы");
            System.out.println("    5) Выйти из учетной записи");

            Integer number = InputUtils.userChoice(scanner);

            switch (number) {
                case 1:
                    showAllProblems(false, false, true, null, null, false);
                    break;
                case 2:
                    createProblem();
                    break;
                case 3:
                    deleteProblem();
                    break;
                case 4:
                    evaluateProblem();
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
        problem.setResolved(false);
        problem = problemProducer.save(problem);

        List<Solution> solutions = new ArrayList<>();
        solutions.add(createSolution(problem, 0));

        String yn;
        int order = 1;
        do {
            System.out.print("    Хоте ли бы еще добавить решение? (да/нет)   ");
            yn = scanner.nextLine();

            if (yn.compareToIgnoreCase("да") == 0) {
                solutions.add(createSolution(problem, order));
                order++;
            }
        } while (yn.compareToIgnoreCase("нет") != 0);
        solutionProducer.saveAll(solutions, problem.getId());

        System.out.println("Проблема и её решения успешно созданы.");
    }

    private Solution createSolution(Problem problem, int order) {
        System.out.println();
        System.out.println("        Добавляем альтернативное решение.");
        System.out.print("        Введите решение: ");
        String header = scanner.nextLine();

        Solution solution = new Solution();
        solution.setProblem(problem);
        solution.setHeader(header);
        solution.setRating(0.0);
        solution.setOrder(order);
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

    private void evaluateProblem() {
        if (problemIdForTask == null) {
            System.out.println("Проблема не была выбрана.");
            return;
        }

        int[][] evaluationMatrix = problemProducer.getSolutionMatrixByProblemId(problemIdForTask);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < evaluationMatrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < evaluationMatrix[i].length; j++) {
                sum += evaluationMatrix[i][j];
            }
            map.put(i, sum);
        }

        List<Solution> solutions = solutionProducer.findSolutions(null, problemIdForTask, true, null, null, null);
        for (Solution solution : solutions) {
            System.out.println("    Sum: " + map.get(solution.getOrder()) + " -- Header: " + solution.getHeader() + "");
        }
    }

}
