package com.ak.work.client.menu;

import com.ak.work.client.entity.*;
import com.ak.work.client.producer.ProblemProducer;
import com.ak.work.client.producer.SolutionProducer;
import com.ak.work.client.producer.UserProducer;
import com.ak.work.client.util.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    @Autowired
    protected ProblemProducer problemProducer;

    @Autowired
    protected SolutionProducer solutionProducer;

    @Autowired
    protected UserProducer userProducer;

    protected Manager manager;

    protected Expert expert;

    protected Admin admin;

    protected Scanner scanner;

    /**
     * в переменной храним выбранную проблему, чтобы сохранялась между действий
     */
    protected Integer problemIdForTask;

    /**
     * сохраняем первое выбранное решение для сравнения
     */
    protected Integer solutionOrderToTask;

    /**
     * сохраняем второе выбранное решение для сравнения
     */
    protected Integer childSolutionOrderToTask;

    protected List<Solution> solutions;

    public abstract void start(User user, Scanner scanner);

    protected void showAllProblems(boolean withInput, boolean resolved, boolean all, Boolean isRow, Integer row, Boolean inverse) {
        //true - когда нажали выход
        boolean isExit = showProblemsWithoutSolutions(resolved);
        if (problemIdForTask == null || isExit) {
            return;
        }

        //находим решения проблем
        this.solutions = solutionProducer.findSolutions(expert != null ? expert.getId() : null, problemIdForTask, all, isRow, row, inverse);

        /*если ничего не нашли, то мы столкунулись с ситуацией, когда эксперт начал оценивать
          какую-то проблему и исходя из алгоритма мы ищем сначала доступные (неоценённые) строки,
          а после у каждой выбранной строки матрицы выбираем доступные (неоценённые) столбцы.
          Но главный недостаток данных действий - у строки могут быть заняты не все столбцы,
          в то время как алгоритм считает, что они заняты полностью. Следовательно, нужно учесть
          данную ситуацию и подтянуть из базы данных все строки матрицы (все решения определенной
          проблемы) и по-одиночке проверить: есть ли свободные у них столбцы? (что и делается
          строками ниже)
         */
        if (this.solutions.isEmpty()) {

            //находим все решения проблемы
            List<Solution> temp = solutionProducer.findSolutions(
                    expert.getId(), problemIdForTask, true, false,
                    null, false);

            //итерируем полученные решения
            for (Solution solution : temp) {
                /*
                    ходим на сервер, чтобы узнать: есть ли доступные
                    столбцы для оценивания у данной (solution.getOrder()) строки
                 */
                List<Solution> q = solutionProducer.findSolutions(
                        expert.getId(), problemIdForTask, false, true,
                        solution.getOrder(), false);

                /*
                    если есть доступная строка, то начинаем работать с ней
                    (работать == находим у нее доступные столбцы и выводим пользователю
                    для выбора
                 */
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

        System.out.println();
        System.out.println("        Eё альтернативные решения:");
        showPossibleProblemSolutions(solutions, false, withInput);
    }

    protected boolean showProblemsWithoutSolutions(boolean resolved) {
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
            return true;
        }

        Problem problem = problems.get(--choice);
        problemIdForTask = problem.getId();
        System.out.format("        Вы выбрали проблему: %d) %s", (choice + 1), problem.getHeader());
        return false;
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
            if (choice >= counter || choice <= 0) {
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
